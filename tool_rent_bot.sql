-- ============================================
-- Tool Rent Bot — PostgreSQL init (DDL)
-- ============================================

CREATE EXTENSION IF NOT EXISTS btree_gist;


CREATE TABLE IF NOT EXISTS roles (
                                     id   BIGSERIAL PRIMARY KEY,
                                     name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
                                     id         BIGSERIAL PRIMARY KEY,
                                     alias      TEXT,
                                     name       TEXT NOT NULL,
                                     surname    TEXT,
                                     role_id    BIGINT NOT NULL REFERENCES roles(id),
    chat_id    TEXT NOT NULL,
    blacklist  BOOLEAN NOT NULL DEFAULT false,
    approve    BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
    );

CREATE INDEX IF NOT EXISTS idx_users_role ON users(role_id);
CREATE INDEX IF NOT EXISTS idx_users_chat ON users(chat_id);
CREATE TABLE IF NOT EXISTS rent_types (
                                          id   BIGSERIAL PRIMARY KEY,
                                          name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS tools (
                                     id            BIGSERIAL PRIMARY KEY,
                                     name          TEXT NOT NULL,
                                     price_cents   BIGINT NOT NULL CHECK (price_cents >= 0),
    callback_data TEXT,
    status        TEXT NOT NULL DEFAULT 'active' CHECK (status IN ('active','archived')),
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now()
    );

CREATE TABLE IF NOT EXISTS orders (
                                      id           BIGSERIAL PRIMARY KEY,
                                      user_id      BIGINT NOT NULL REFERENCES users(id),
    approved     BOOLEAN NOT NULL DEFAULT false,
    start_date   DATE,
    end_date     DATE,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    CHECK (
(start_date IS NULL AND end_date IS NULL)
    OR
(start_date IS NOT NULL AND end_date IS NOT NULL AND end_date > start_date)
    )
    );

CREATE INDEX IF NOT EXISTS idx_orders_user      ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_orders_dates     ON orders(start_date, end_date);

-- 6) Позиции заказа (явная M2M между orders и tools)
CREATE TABLE IF NOT EXISTS order_items (
                                           id          BIGSERIAL PRIMARY KEY,
                                           order_id    BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    tool_id     BIGINT NOT NULL REFERENCES tools(id),
    start_date  DATE NOT NULL,
    end_date    DATE NOT NULL,
    price_cents BIGINT NOT NULL CHECK (price_cents >= 0),
    CHECK (end_date > start_date)
    );

CREATE INDEX IF NOT EXISTS idx_order_items_order ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_order_items_tool  ON order_items(tool_id);


CREATE OR REPLACE FUNCTION order_items_copy_dates()
    RETURNS trigger AS $$
BEGIN
    IF NEW.start_date IS NULL OR NEW.end_date IS NULL THEN
SELECT o.start_date, o.end_date
INTO NEW.start_date, NEW.end_date
FROM orders o
WHERE o.id = NEW.order_id;

IF NEW.start_date IS NULL OR NEW.end_date IS NULL THEN
            RAISE EXCEPTION 'Order % has no dates; cannot insert order_item without dates', NEW.order_id;
END IF;
END IF;

    IF NEW.end_date <= NEW.start_date THEN
        RAISE EXCEPTION 'end_date (%) must be greater than start_date (%)', NEW.end_date, NEW.start_date;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_order_items_copy_dates ON order_items;
CREATE TRIGGER trg_order_items_copy_dates
    BEFORE INSERT ON order_items
    FOR EACH ROW
    EXECUTE FUNCTION order_items_copy_dates();

DO $$
BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_constraint
            WHERE conname = 'order_items_no_overlap'
        ) THEN
ALTER TABLE order_items
    ADD CONSTRAINT order_items_no_overlap
    EXCLUDE USING gist (
                    tool_id WITH =,
                    daterange(start_date, end_date, '[)') WITH &&
                    );
END IF;
    END$$;

INSERT INTO roles (name) VALUES ('admin') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name) VALUES ('user')  ON CONFLICT (name) DO NOTHING;

INSERT INTO rent_types (name) VALUES ('day')   ON CONFLICT (name) DO NOTHING;
INSERT INTO rent_types (name) VALUES ('hour')  ON CONFLICT (name) DO NOTHING;
INSERT INTO rent_types (name) VALUES ('week')  ON CONFLICT (name) DO NOTHING;
