Feature: Flujo CRUD de usuarios en PetStore API

  Como equipo de QA
  Quiero validar el flujo completo de gestion de usuarios
  Para asegurar que crear, consultar, actualizar y eliminar funciona de forma independiente

  @regresion @crud
  Scenario: Ejecutar el flujo CRUD completo de un usuario
    Given que el actor tiene los datos de un nuevo usuario unico
    When el actor crea el usuario mediante POST
    Then la respuesta de creacion es exitosa con codigo 200

    When el actor consulta los datos del usuario creado mediante GET
    Then el username del usuario devuelto coincide con el creado

    When el actor actualiza la informacion del usuario creado mediante PUT
    Then la respuesta de actualizacion es exitosa con codigo 200

    When el actor consulta los datos del usuario creado mediante GET
    Then el campo "firstName" del usuario devuelto es "Updated"

    When el actor elimina el usuario creado mediante DELETE
    Then la respuesta de eliminacion es exitosa con codigo 200

    When el actor consulta los datos del usuario creado mediante GET
    Then la consulta del usuario es fallida con codigo 404
