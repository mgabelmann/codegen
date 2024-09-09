/* example of a @OneToOne since the shipment_id is unique, otherwise it would be @ManyToOne
   NOTE: the UNIQUE could also be on the column definition
*/
CREATE TABLE PRODUCT (
    id INTEGER PRIMARY KEY,
    shipment_id INTEGER,
    --shipment_id INTEGER UNIQUE,
    create_dtm TEXT NOT NULL,


    --UNIQUE(shipment_id),
    FOREIGN KEY (shipment_id) REFERENCES SHIPMENT (id)
);

CREATE TABLE SHIPMENT (
  id INTEGER PRIMARY KEY,
  create_dtm TEXT NOT NULL
);
