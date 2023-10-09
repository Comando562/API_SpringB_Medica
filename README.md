### TALLER DE FORMACION SPRING BOOT
@Oracle @AluraLatam
## ⚙️ Herramientas utilizadas de Desarrollo
- Spring Boot, Spring Security, Spring Filter
- Hibernate, Flyway
- MySQL, Lombok, OpenApi, DTOs, Migrations
# Aplicacion Medica API Voll Med

Este proyecto fue Generado con Java version 20, Spring boot version 3.0.2
## INTRODUCCION
- API REST, CRUD
- Registro de Medicos, Eliminacion Dinamica (Acitvo) de Medicos , asi como registro de pacientes y realizacion de consultas asi como su cancelacion con motivo.
- Validaciones
- Tratamiento de errores
- Control de acceso con JWT

![1](https://github.com/Comando562/API_SpringB_Medica/assets/119273389/6bc92e8b-1bb3-4208-b5fd-1aa5d3a0c894)


##Implementaciones en Reglas de negocio
- Registro Medico:
Nombre
Correo electrónico
Teléfono
documento
Especialidad (Ortopedia, Cardiología, Ginecología o Dermatología)
Dirección completa (calle, número, complemento, barrio, ciudad, estado y código postal)
(Toda la información es obligatoria, excepto el número y el complemento de la dirección)

- Listado Medico: 
-Nombre
-Correo electrónico
-documento
-Especialidad
(La lista  esta ordenada por nombre del médico en orden ascendente y paginada, mostrando 10 registros por página)

- Actualizacion Medicos: 
El sistema debe tiene una funcionalidad de actualización de los datos de registro de médicos en la que se podrán actualizar las siguientes informaciones:

 -Nombre
-Teléfono
-Dirección
El sistema debe validar las siguientes reglas de negocio:

 No permite la modificación del correo electrónico del médico;
No permite la modificación del documento del médico;
No permite la modificación de la especialidad del médico.

- Eliminacion Medicos : 
El sistema debe tiene una funcionalidad que permita la eliminación de médicos registrados.

El sistema valida las siguientes reglas de negocio:

(La eliminación no debe borrar los datos del médico, sino que debe hacer que el médico sea "inactivo" en el sistema.)

- Registro Pacientes: 
-Nombre
-Correo electrónico
-Teléfono
-documento
Dirección completa (calle, número, complemento, barrio, ciudad, estado y código postal)
Toda la información es obligatoria, excepto el número y el complemento de la dirección.

- Listado de Pacientes: 
El sistema tiene una funcionalidad de listado de pacientes en la que se muestra la siguiente información de cada uno de los pacientes registrados:

 -Nombre
-Correo electrónico
-documento
La lista debe estar ordenada por nombre del paciente en orden ascendente y paginada, mostrando 10 registros por página.

- Actualizacion Pacientes: 
El sistema tiene una funcionalidad de actualización de los datos de registro de pacientes en la que se podrán actualizar las siguientes informaciones:

 -Nombre
-Teléfono
-Dirección

 El sistema valida las siguientes reglas de negocio:

 -No permite la modificación del correo electrónico del paciente;
-No permite la modificación del documento del paciente.

- Eliminacion de Pacientes:
 El sistema debe tener una funcionalidad que permita la eliminación de pacientes registrados.

 El sistema debe validar las siguientes reglas de negocio:

 (La eliminación no debe borrar los datos del paciente, sino que debe hacer que el paciente sea "inactivo" en el sistema.)
 
- Cancelamiento de consultas
  El sistema cuenta con una funcionalidad que permite la cancelación de consultas, en la cual se delega la siguiente informacion:

 -Consulta
-Motivo de la cancelación

 Las siguientes reglas de negocio deben ser validadas por el sistema:
Es obligatorio informar el motivo de la cancelación de la consulta, entre las opciones: paciente se retiró, médico canceló u otras;
-Una cita solo se puede cancelar con al menos 24 horas de anticipación.

- Agendamiento de consultas:
El sistema cuenta con una funcionalidad que permite agendar citas, en la cual se debe llenar la siguiente información:

 - Paciente
 - Medico
 - Consulta fecha/hora
 Las siguientes reglas de negocio deben ser validadas por el sistema:
 - El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas;
 - Las consultas tienen una duración fija de 1 hora;
 - Las consultas deben programarse con al menos 30 minutos de anticipación;
 - No permite agendar citas con pacientes inactivos en el sistema;
 - No permite programar citas con médicos inactivos en el sistema;
 - No permie programar más de una consulta en el mismo día para el mismo paciente;
 - No permite programar una cita con un médico que ya tiene otra cita programada en la misma fecha/hora;
 - La elección de un médico es opcional, en cuyo caso de que no exista el id el sistema debe elige aleatoriamente un médico que esté disponible en la fecha/hora ingresada.

## 🎥 Demostración
 - NOTA: Se debe realizar primero el Login con el usuario para generar el token, y posteriormente en cualquier metodo en Autenticacion Barer Token; Pegar el token que se genero para autenticar la request HTTP
 
![2](https://github.com/Comando562/API_SpringB_Medica/assets/119273389/f54273c3-f0ef-4299-a62d-d2ba7cf059a5)
![3](https://github.com/Comando562/API_SpringB_Medica/assets/119273389/81b48986-1b78-4d51-8e02-8fc13379ad16)

![ezgif com-video-to-gif (1)](https://github.com/Comando562/API_SpringB_Medica/assets/119273389/86f10363-1525-47ee-98c4-568b986244c6)


## 👤 Contacto
Si quieres compartir alguna observación, comentario o consulta acerca de dicho proyecto o algo relacionado a la programación, puedes escribirme a los siguientes medios: 
- [Linkedin](https://www.linkedin.com/in/leonardo562/)
- [Correo Electrónico](mailto:leo.moya562@gmail.com)
