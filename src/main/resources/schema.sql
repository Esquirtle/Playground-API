CREATE TABLE IF NOT EXISTS phones (
  phone_number TEXT PRIMARY KEY,
  owner_id TEXT,
  created_at INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS contacts (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  phone_number TEXT NOT NULL,
  contact_number TEXT NOT NULL,
  display_name TEXT,
  created_at INTEGER NOT NULL,
  UNIQUE(phone_number, contact_number)
);

CREATE TABLE IF NOT EXISTS conversations (
  conversation_id TEXT PRIMARY KEY,
  phone_number TEXT NOT NULL,
  contact_number TEXT NOT NULL,
  last_message_at INTEGER NOT NULL,
  last_message_body TEXT,
  unread_count INTEGER NOT NULL DEFAULT 0,
  updated_at INTEGER NOT NULL,
  UNIQUE(phone_number, contact_number)
);

CREATE TABLE IF NOT EXISTS messages (
  message_id TEXT PRIMARY KEY,
  conversation_id TEXT NOT NULL,
  sender_number TEXT NOT NULL,
  recipient_number TEXT NOT NULL,
  body TEXT,
  sent_at INTEGER NOT NULL,
  is_sender_copy INTEGER NOT NULL,
  created_at INTEGER NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_messages_conversation_id ON messages(conversation_id);
CREATE INDEX IF NOT EXISTS idx_conversations_phone ON conversations(phone_number);
CREATE INDEX IF NOT EXISTS idx_contacts_phone ON contacts(phone_number);
