-- MASS OF DATA PEOPLE --
INSERT INTO tb_people_manager(name, birthday, gender, parentage)
    VALUES('Gregory', '1989-12-28', 'M', 'Father');

-- MASS OF DATA ADDRESS --
INSERT INTO tb_address_register(street, number, district, city, state, people_id)
    VALUES('Alameda Porcelana', 55, 'Ceramica', 'Sao Caetano do Sul', 'SP', 1);

-- MASS OF DATA HOME APPLIANCE --
INSERT INTO tb_home_appliance_manager(name, model, brand, voltage, people_id)
    VALUES('Geladeira', 'Platinium', 'Samsung', 110, 1);