-- src/main/resources/schema.sql


-- Tabla para Docentes (Mock para referencia en RAA y Rúbricas)
CREATE TABLE docente (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Tabla para Estudiantes (Mock para referencia en Evaluaciones)
CREATE TABLE estudiante (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    codigo VARCHAR(255) UNIQUE NOT NULL,
    programa_academico_id VARCHAR(255)
    -- En un sistema real, tendría una FK a programa_academico(id)
);

-- Tabla para Evaluadores Externos (Mock para referencia en Evaluaciones)
CREATE TABLE evaluador_externo (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    organizacion VARCHAR(255)
);

-- Tabla para Programa Académico (Mock para referencia en Asignaturas y Competencias)
CREATE TABLE programa_academico (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla para Asignaturas (Mock para referencia en RAA)
CREATE TABLE asignatura (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    codigo VARCHAR(255) UNIQUE NOT NULL,
    docente_id VARCHAR(255),
    programa_academico_id VARCHAR(255),
    FOREIGN KEY (docente_id) REFERENCES docente(id),
    FOREIGN KEY (programa_academico_id) REFERENCES programa_academico(id)
);

-- Tabla para Competencias del Programa (Mock para referencia en RA Programa y RAA)
CREATE TABLE competencia_programa (
    id VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(500) NOT NULL,
    programa_academico_id VARCHAR(255),
    FOREIGN KEY (programa_academico_id) REFERENCES programa_academico(id)
);

-- Tabla para Resultados de Aprendizaje del Programa (Mock para referencia en RAA)
CREATE TABLE resultado_aprendizaje_programa (
    id VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(500) NOT NULL,
    competencia_programa_id VARCHAR(255),
    FOREIGN KEY (competencia_programa_id) REFERENCES competencia_programa(id)
);


-- ************************************************************
-- Entidades Centrales de nuestro Dominio de Negocio
-- ************************************************************

-- Tabla para Resultados de Aprendizaje por Asignatura (RAA)
CREATE TABLE resultado_aprendizaje_asignatura (
    id VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(500) NOT NULL,
    asignatura_id VARCHAR(255) NOT NULL,
    resultado_aprendizaje_programa_id VARCHAR(255) NOT NULL,
    competencia_programa_id VARCHAR(255) NOT NULL,
    docente_id VARCHAR(255) NOT NULL,
    periodo_academico VARCHAR(50) NOT NULL, -- Ej: "2024-1", "2024-2"
    FOREIGN KEY (asignatura_id) REFERENCES asignatura(id),
    FOREIGN KEY (resultado_aprendizaje_programa_id) REFERENCES resultado_aprendizaje_programa(id),
    FOREIGN KEY (competencia_programa_id) REFERENCES competencia_programa(id),
    FOREIGN KEY (docente_id) REFERENCES docente(id)
);

-- Tabla para Rúbricas
CREATE TABLE rubrica (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    resultado_aprendizaje_asignatura_id VARCHAR(255) NOT NULL,
    docente_id VARCHAR(255) NOT NULL, -- El docente que crea la rúbrica
    esta_publicada BOOLEAN DEFAULT FALSE, -- Podría ser un flag para "lista para usar"
    FOREIGN KEY (resultado_aprendizaje_asignatura_id) REFERENCES resultado_aprendizaje_asignatura(id),
    FOREIGN KEY (docente_id) REFERENCES docente(id)
);

-- Tabla para Criterios de Evaluación dentro de una Rúbrica
CREATE TABLE criterio_evaluacion (
    id VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(500) NOT NULL,
    ponderacion DECIMAL(5, 2) NOT NULL CHECK (ponderacion >= 0 AND ponderacion <= 100),
    rubrica_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (rubrica_id) REFERENCES rubrica(id)
);

-- Tabla para Niveles de Desempeño dentro de un Criterio
CREATE TABLE nivel_desempeno (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    nota_minima DECIMAL(4, 2) NOT NULL,
    nota_maxima DECIMAL(4, 2) NOT NULL,
    criterio_evaluacion_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (criterio_evaluacion_id) REFERENCES criterio_evaluacion(id),
    CHECK (nota_minima >= 0 AND nota_maxima <= 5.0) -- Asumiendo escala de 0 a 5
);

-- Tabla para la Evaluación de un Estudiante utilizando una Rúbrica
CREATE TABLE evaluacion (
    id VARCHAR(255) PRIMARY KEY,
    rubrica_id VARCHAR(255) NOT NULL,
    estudiante_id VARCHAR(255) NOT NULL,
    evaluador_id VARCHAR(255) NOT NULL, -- Puede ser docente o evaluador externo
    tipo_evaluador VARCHAR(50) NOT NULL, -- 'DOCENTE' o 'EVALUADOR_EXTERNO'
    fecha_evaluacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    nota_final DECIMAL(4, 2), -- La nota calculada automáticamente
    retroalimentacion_general VARCHAR(1000),
    FOREIGN KEY (rubrica_id) REFERENCES rubrica(id)
    -- No hay FOREIGN KEY directa a estudiante_id o evaluador_id aquí, ya que el tipo_evaluador es dinámico.
    -- La validación de que el ID exista debe hacerse a nivel de servicio.
);

-- Tabla para el Detalle de cada Criterio Evaluado en una Evaluación
CREATE TABLE detalle_evaluacion (
    id VARCHAR(255) PRIMARY KEY,
    evaluacion_id VARCHAR(255) NOT NULL,
    criterio_evaluacion_id VARCHAR(255) NOT NULL,
    nivel_desempeno_seleccionado_id VARCHAR(255) NOT NULL,
    retroalimentacion_especifica VARCHAR(1000),
    FOREIGN KEY (evaluacion_id) REFERENCES evaluacion(id),
    FOREIGN KEY (criterio_evaluacion_id) REFERENCES criterio_evaluacion(id),
    FOREIGN KEY (nivel_desempeno_seleccionado_id) REFERENCES nivel_desempeno(id)
);