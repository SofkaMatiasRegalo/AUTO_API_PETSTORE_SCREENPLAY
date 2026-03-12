Feature: Eliminacion de usuarios en PetStore API

  Como equipo de QA
  Quiero validar el servicio DELETE del modulo de usuarios
  Para garantizar que los usuarios eliminados ya no quedan disponibles para consulta

  @eliminar @regresion
  Scenario Outline: Eliminar un usuario existente y confirmar su ausencia
    Given existe un usuario registrado con username "<username>"
    When  el actor elimina el usuario "<username>" mediante DELETE
    Then  la respuesta de eliminacion es exitosa con codigo <statusCode>
    When  el actor consulta los datos del usuario "<username>" mediante GET
    Then  la consulta del usuario es fallida con codigo 404

    Examples:
      | username                  | statusCode |
      | delete_user_screenplay_1  | 200        |
