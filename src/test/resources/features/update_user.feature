Feature: Actualizacion de usuarios en PetStore API

  Como equipo de QA
  Quiero validar el servicio PUT del modulo de usuarios
  Para garantizar que los cambios persisten sobre usuarios existentes

  @actualizar @regresion
  Scenario Outline: Actualizar un usuario existente y validar la persistencia del cambio
    Given existe un usuario registrado con username "<username>"
    When  el actor actualiza la informacion del usuario "<username>" mediante PUT
    Then  la respuesta de actualizacion es exitosa con codigo <statusCode>

    Examples:
      | username                  | statusCode |
      | update_user_screenplay_1  | 200        |
