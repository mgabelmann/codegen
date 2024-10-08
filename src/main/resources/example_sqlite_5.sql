CREATE TABLE address (
    address_id INTEGER PRIMARY KEY,
    person_id INTEGER NOT NULL,
    first_name TEXT NOT NULL,
    middle_name TEXT,
    last_name TEXT NOT NULL,
    st_address TEXT NOT NULL,
    city TEXT NOT NULL,
    postal_code TEXT NOT NULL,

    create_dtm TEXT NOT NULL,
    active TEXT DEFAULT 'Y' NOT NULL,

	FOREIGN KEY (person_id)
          REFERENCES person (person_id)
             ON DELETE CASCADE
             ON UPDATE NO ACTION
);

