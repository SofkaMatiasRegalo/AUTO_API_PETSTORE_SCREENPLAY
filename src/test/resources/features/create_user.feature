Feature: Creacion de usuarios en PetStore API

  Como equipo de QA
  Quiero validar el servicio POST del modulo de usuarios
  Para garantizar que se pueden crear usuarios correctamente

  @crear @regresion
  Scenario Outline: Crear un usuario exitosamente mediante POST
    Given que el actor tiene los datos de un nuevo usuario "<username>"
    When  el actor crea el usuario mediante POST
    Then  la respuesta de creacion es exitosa con codigo <statusCode>

    Examples:
      | username              | statusCode |
      | testuser_screenplay_1 | 200        |