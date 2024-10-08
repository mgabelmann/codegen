CREATE TABLE contact_group (
   contact_id INTEGER,
   group_id INTEGER NOT NULL,
   local1_id INTEGER NOT NULL,
   local2_id INTEGER NOT NULL,

   PRIMARY KEY (contact_id, group_id),

   FOREIGN KEY (contact_id)
      REFERENCES contacts (contact_id)
         ON DELETE CASCADE
         ON UPDATE NO ACTION,

   FOREIGN KEY (local1_id, local2_id)
         REFERENCES groups (remote1_id, remote2_id)
            ON UPDATE NO ACTION
            ON DELETE CASCADE
);
