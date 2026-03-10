Feature: Ciclo CRUD completo de usuarios en PetStore API

  Como equipo de QA
  Quiero validar los servicios REST del modulo de usuarios PetStore
  Para garantizar la integridad de las operaciones POST, GET, PUT y DELETE

  @crud @regresion
  Scenario: Flujo CRUD - Crear, consultar, actualizar y eliminar un usuario en un solo flujo
    Given que el actor tiene los datos de un nuevo usuario "testuser_screenplay"
    When  el actor crea el usuario mediante POST
    Then  la respuesta de creacion es exitosa con codigo 200
    When  el actor consulta los datos del usuario "testuser_screenplay" mediante GET
    Then  el username del usuario devuelto es "testuser_screenplay"
    When  el actor actualiza la informacion del usuario "testuser_screenplay" mediante PUT
    Then  la respuesta de actualizacion es exitosa con codigo 200
    When  el actor elimina el usuario "testuser_screenplay" mediante DELETE
    Then  la respuesta de eliminacion es exitosa con codigo 200
