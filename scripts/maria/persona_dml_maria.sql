INSERT INTO 
	`persona_db`.`persona`(`cc`,`nombre`,`apellido`,`genero`,`edad`) 
VALUES
	(123456789,'Pepe','Perez','M',30),
	(987654321,'Pepito','Perez','M',null),
	(321654987,'Pepa','Juarez','F',30),
	(147258369,'Pepita','Juarez','F',10),
	(963852741,'Fede','Perez','M',18);

INSERT INTO 
    `persona_db`.`profesion`(`id`, `nom`, `des`) 
VALUES
    (1, 'Ingeniero de Software', 'Profesional especializado en desarrollo de software'),
    (2, 'Médico General', 'Profesional de la salud especializado en medicina general'),
    (3, 'Profesor', 'Profesional dedicado a la enseñanza'),
    (4, 'Arquitecto', 'Profesional especializado en diseño y construcción'),
    (5, 'Contador', 'Profesional especializado en contabilidad y finanzas');

INSERT INTO
    `persona_db`.`telefono`(`num`, `oper`, `duenio`)
VALUES
    ('3001234567', 'Claro', 123456789),    -- Teléfono de Pepe
    ('3009876543', 'Movistar', 987654321), -- Teléfono de Pepito
    ('3002345678', 'Tigo', 321654987),     -- Teléfono de Pepa
    ('3007654321', 'Claro', 147258369),    -- Teléfono de Pepita
    ('3005678901', 'Movistar', 963852741); -- Teléfono de Fede

INSERT INTO
    `persona_db`.`estudios`(`id_prof`, `cc_per`, `fecha`, `univer`)
VALUES
    (1, 123456789, '2015-06-30', 'Universidad Nacional'),    -- Pepe es Ingeniero
    (2, 987654321, '2016-07-15', 'Universidad de Los Andes'), -- Pepito es Médico
    (3, 321654987, '2015-12-20', 'Universidad Javeriana'),   -- Pepa es Profesora
    (1, 963852741, '2023-06-30', 'Universidad Nacional'),    -- Fede es Ingeniero
    (5, 123456789, '2018-06-30', 'Universidad Nacional');    -- Pepe también es Contador