DROP TABLE integrantes;

CREATE TABLE integrantes (
    id BIGINT IDENTITY PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fecha_incorporacion DATE NOT NULL,
    salario DOUBLE NOT NULL,
    pais VARCHAR(50) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    especialidad VARCHAR(20),
    posicion VARCHAR(20),
    dorsal INT,
    altura DOUBLE,
    peso DOUBLE,
    goles INT,
    partidos_jugados INT,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    isDeleted BOOLEAN DEFAULT FALSE
);
