CREATE TABLE contacts (
	contact_id INTEGER PRIMARY KEY,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	email TEXT NOT NULL UNIQUE,
	phone TEXT NOT NULL UNIQUE,
	birth_dt TEXT NOT NULL,
	create_dtm TEXT DEFAULT (datetime('now','localtime')),
	quantity INTEGER DEFAULT 0
);
