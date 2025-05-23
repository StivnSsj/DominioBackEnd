-- src/main/resources/data.sql

-- Datos para tablas "mock" (para satisfacer las FKs de nuestro dominio)

-- Docentes
INSERT INTO docente (id, nombre, email) VALUES
('docente-1', 'Juan Pérez', 'juan.perez@uncauca.edu.co'),
('docente-2', 'María García', 'maria.garcia@uncauca.edu.co'),
('docente-3', 'Carlos Sánchez', 'carlos.sanchez@uncauca.edu.co');

-- Programas Académicos
INSERT INTO programa_academico (id, nombre) VALUES
('programa-1', 'Ingeniería de Sistemas'),
('programa-2', 'Electrónica y Telecomunicaciones');

-- Asignaturas
INSERT INTO asignatura (id, nombre, codigo, docente_id, programa_academico_id) VALUES
('asignatura-1', 'Bases de Datos I', 'SIS-BD1', 'docente-1', 'programa-1'),
('asignatura-2', 'Programación Orientada a Objetos', 'SIS-POO', 'docente-2', 'programa-1'),
('asignatura-3', 'Circuitos Digitales', 'ET-CD', 'docente-3', 'programa-2');

-- Estudiantes
INSERT INTO estudiante (id, nombre, codigo, programa_academico_id) VALUES
('estudiante-1', 'Laura Torres', '104567890', 'programa-1'),
('estudiante-2', 'Pedro Ramirez', '104567891', 'programa-1'),
('estudiante-3', 'Ana López', '104567892', 'programa-2');

-- Evaluadores Externos
INSERT INTO evaluador_externo (id, nombre, organizacion) VALUES
('evaluador-1', 'Empresa Tech Solutions', 'Tech Solutions S.A.S.'),
('evaluador-2', 'Consultoría Innova', 'Innova Group');

-- Competencias del Programa
INSERT INTO competencia_programa (id, descripcion, programa_academico_id) VALUES
('comp-prog-1', 'Diseña y desarrolla soluciones de software eficientes y escalables.', 'programa-1'),
('comp-prog-2', 'Aplica principios de ingeniería de sistemas para la resolución de problemas complejos.', 'programa-1'),
('comp-prog-3', 'Diseña e implementa sistemas electrónicos de comunicación.', 'programa-2');

-- Resultados de Aprendizaje del Programa
INSERT INTO resultado_aprendizaje_programa (id, descripcion, competencia_programa_id) VALUES
('ra-prog-1', 'Construye modelos de bases de datos relacionales.', 'comp-prog-1'),
('ra-prog-2', 'Desarrolla aplicaciones web utilizando frameworks modernos.', 'comp-prog-1'),
('ra-prog-3', 'Analiza y evalúa sistemas de comunicación digital.', 'comp-prog-3');


-- ************************************************************
-- Datos para nuestro Dominio de Negocio
-- ************************************************************

-- Resultados de Aprendizaje por Asignatura (RAA)
INSERT INTO resultado_aprendizaje_asignatura (id, descripcion, asignatura_id, resultado_aprendizaje_programa_id, competencia_programa_id, docente_id, periodo_academico) VALUES
('raa-1', 'Diseña y normaliza esquemas de bases de datos relacionales para un caso de estudio dado.', 'asignatura-1', 'ra-prog-1', 'comp-prog-1', 'docente-1', '2024-1'),
('raa-2', 'Implementa consultas SQL complejas para manipulación y recuperación de datos.', 'asignatura-1', 'ra-prog-1', 'comp-prog-1', 'docente-1', '2024-1'),
('raa-3', 'Aplica patrones de diseño en el desarrollo de aplicaciones Java.', 'asignatura-2', 'ra-prog-2', 'comp-prog-1', 'docente-2', '2024-1');

-- Rúbricas
INSERT INTO rubrica (id, nombre, resultado_aprendizaje_asignatura_id, docente_id, esta_publicada) VALUES
('rubrica-1', 'Rúbrica de Diseño de Bases de Datos', 'raa-1', 'docente-1', TRUE),
('rubrica-2', 'Rúbrica de Implementación de Consultas SQL', 'raa-2', 'docente-1', FALSE),
('rubrica-3', 'Rúbrica de Desarrollo de Aplicaciones Java', 'raa-3', 'docente-2', TRUE);

-- Criterios de Evaluación
INSERT INTO criterio_evaluacion (id, descripcion, ponderacion, rubrica_id) VALUES
('criterio-1-1', 'Normalización del esquema (1FN, 2FN, 3FN)', 40.00, 'rubrica-1'),
('criterio-1-2', 'Consistencia e integridad de datos', 30.00, 'rubrica-1'),
('criterio-1-3', 'Claridad y documentación del diseño', 30.00, 'rubrica-1'),

('criterio-2-1', 'Corrección y eficiencia de las consultas', 50.00, 'rubrica-2'),
('criterio-2-2', 'Manejo de transacciones y errores', 30.00, 'rubrica-2'),
('criterio-2-3', 'Uso de funciones avanzadas de SQL', 20.00, 'rubrica-2'),

('criterio-3-1', 'Implementación de patrones de diseño (MVC, DAO)', 45.00, 'rubrica-3'),
('criterio-3-2', 'Calidad del código (legibilidad, comentarios)', 30.00, 'rubrica-3'),
('criterio-3-3', 'Pruebas unitarias y de integración', 25.00, 'rubrica-3');

-- Niveles de Desempeño
INSERT INTO nivel_desempeno (id, nombre, descripcion, nota_minima, nota_maxima, criterio_evaluacion_id) VALUES
('nivel-1-1-1', 'Insuficiente', 'El esquema no cumple con los principios básicos de normalización.', 0.00, 2.99, 'criterio-1-1'),
('nivel-1-1-2', 'Aceptable', 'El esquema presenta algunos errores de normalización, pero es funcional.', 3.00, 3.99, 'criterio-1-1'),
('nivel-1-1-3', 'Excelente', 'El esquema está completamente normalizado y optimizado.', 4.00, 5.00, 'criterio-1-1'),

('nivel-1-2-1', 'Insuficiente', 'Hay problemas significativos de integridad de datos.', 0.00, 2.99, 'criterio-1-2'),
('nivel-1-2-2', 'Aceptable', 'La consistencia de datos es adecuada, con algunas excepciones menores.', 3.00, 3.99, 'criterio-1-2'),
('nivel-1-2-3', 'Excelente', 'El diseño asegura una total consistencia e integridad de datos.', 4.00, 5.00, 'criterio-1-2'),

('nivel-2-1-1', 'Insuficiente', 'Las consultas son incorrectas o extremadamente ineficientes.', 0.00, 2.99, 'criterio-2-1'),
('nivel-2-1-2', 'Aceptable', 'Las consultas son correctas pero podrían ser más eficientes.', 3.00, 3.99, 'criterio-2-1'),
('nivel-2-1-3', 'Excelente', 'Consultas correctas, eficientes y optimizadas.', 4.00, 5.00, 'criterio-2-1');

-- Evaluaciones (Ejemplo de una evaluación)
INSERT INTO evaluacion (id, rubrica_id, estudiante_id, evaluador_id, tipo_evaluador, fecha_evaluacion, nota_final, retroalimentacion_general) VALUES
('evaluacion-1', 'rubrica-1', 'estudiante-1', 'docente-1', 'DOCENTE', '2024-05-23 10:00:00-05', 4.50, 'Buen trabajo general, pero mejorar la documentación.'),
('evaluacion-2', 'rubrica-3', 'estudiante-2', 'docente-2', 'DOCENTE', '2024-05-22 14:30:00-05', 3.80, 'Necesita reforzar el uso de pruebas unitarias.');

-- Detalles de Evaluación
INSERT INTO detalle_evaluacion (id, evaluacion_id, criterio_evaluacion_id, nivel_desempeno_seleccionado_id, retroalimentacion_especifica) VALUES
('detalle-1-1', 'evaluacion-1', 'criterio-1-1', 'nivel-1-1-3', 'Normalización impecable.'),
('detalle-1-2', 'evaluacion-1', 'criterio-1-2', 'nivel-1-2-3', 'Excelente manejo de la integridad.'),
('detalle-1-3', 'evaluacion-1', 'criterio-1-3', 'nivel-1-2-2', 'La documentación podría ser más exhaustiva.'),

('detalle-2-1', 'evaluacion-2', 'criterio-3-1', 'nivel-1-1-3', 'Patrones bien aplicados.'),
('detalle-2-2', 'evaluacion-2', 'criterio-3-2', 'nivel-1-2-2', 'Código legible, pero los comentarios son escasos.'),
('detalle-2-3', 'evaluacion-2', 'criterio-3-3', 'nivel-1-1-1', 'Faltan pruebas unitarias significativas.');