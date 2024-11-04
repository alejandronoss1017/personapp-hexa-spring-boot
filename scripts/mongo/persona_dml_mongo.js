db.createCollection("persona");
db.createCollection("estudios");
db.createCollection("profesion");
db.createCollection("telefono");

db.persona.insertMany([
	{
		"_id": NumberInt(123456789),
		"nombre": "Pepe",
		"apellido": "Perez",
		"genero": "M",
		"edad": NumberInt(30),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(987654321),
		"nombre": "Pepito",
		"apellido": "Perez",
		"genero": "M",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(321654987),
		"nombre": "Pepa",
		"apellido": "Juarez",
		"genero": "F",
		"edad": NumberInt(30),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(147258369),
		"nombre": "Pepita",
		"apellido": "Juarez",
		"genero": "F",
		"edad": NumberInt(10),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(963852741),
		"nombre": "Fede",
		"apellido": "Perez",
		"genero": "M",
		"edad": NumberInt(18),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	}
], { ordered: false });

// Insert professions
db.profesion.insertMany([
    {
        "_id": NumberInt(1),
        "nom": "Ingeniero de Software",
        "des": "Profesional especializado en desarrollo de software",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    },
    {
        "_id": NumberInt(2),
        "nom": "Médico General",
        "des": "Profesional de la salud especializado en medicina general",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    },
    {
        "_id": NumberInt(3),
        "nom": "Profesor",
        "des": "Profesional dedicado a la enseñanza",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    },
    {
        "_id": NumberInt(4),
        "nom": "Arquitecto",
        "des": "Profesional especializado en diseño y construcción",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    },
    {
        "_id": NumberInt(5),
        "nom": "Contador",
        "des": "Profesional especializado en contabilidad y finanzas",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
    }
], { ordered: false });

// Insert phone numbers
db.telefono.insertMany([
    {
        "_id": "3001234567",
        "oper": "Claro",
        "duenio": NumberInt(123456789),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3009876543",
        "oper": "Movistar",
        "duenio": NumberInt(987654321),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3002345678",
        "oper": "Tigo",
        "duenio": NumberInt(321654987),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3007654321",
        "oper": "Claro",
        "duenio": NumberInt(147258369),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3005678901",
        "oper": "Movistar",
        "duenio": NumberInt(963852741),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    }
], { ordered: false });

// Insert studies
db.estudios.insertMany([
    {
        "id_prof": NumberInt(1),
        "cc_per": NumberInt(123456789),
        "fecha": new Date("2015-06-30"),
        "univer": "Universidad Nacional",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    },
    {
        "id_prof": NumberInt(2),
        "cc_per": NumberInt(987654321),
        "fecha": new Date("2016-07-15"),
        "univer": "Universidad de Los Andes",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    },
    {
        "id_prof": NumberInt(3),
        "cc_per": NumberInt(321654987),
        "fecha": new Date("2015-12-20"),
        "univer": "Universidad Javeriana",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    },
    {
        "id_prof": NumberInt(1),
        "cc_per": NumberInt(963852741),
        "fecha": new Date("2023-06-30"),
        "univer": "Universidad Nacional",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    },
    {
        "id_prof": NumberInt(5),
        "cc_per": NumberInt(123456789),
        "fecha": new Date("2018-06-30"),
        "univer": "Universidad Nacional",
        "_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
    }
], { ordered: false });