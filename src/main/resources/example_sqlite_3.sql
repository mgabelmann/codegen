--composite primary key
CREATE TABLE MOBILE_SUPPORT_PRODUCT (
   PRODUCT_TYPE_CODE TEXT PRIMARY KEY NOT NULL,
   RESOURCE_TYPE_CODE TEXT PRIMARY KEY NOT NULL,
   EFFECTIVE_DT TEXT NOT NULL,
   EXPIRY_DT TEXT NOT NULL,
   CREATE_DTM TEXT NOT NULL,

   FOREIGN KEY (PRODUCT_TYPE_CODE)
      REFERENCES PRODUCT_TYPE_CODE (PRODUCT_TYPE_CODE)
         ON DELETE CASCADE
         ON UPDATE NO ACTION,

   FOREIGN KEY (RESOURCE_TYPE_CODE)
      REFERENCES RESOURCE_TYPE_CODE (VEHICLE_TYPE_CODE)
         ON DELETE CASCADE
         ON UPDATE NO ACTION

);