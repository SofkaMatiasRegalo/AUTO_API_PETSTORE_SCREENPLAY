Feature: Consulta de usuarios en PetStore API

  Como equipo de QA
  Quiero validar el servicio GET del modulo de usuarios
  Para garantizar que se recupera la informacion correcta de usuarios existentes

  @consultar @regresion
  Scenario Outline: Consultar un usuario existente y validar su identidad
    Given existe un usuario registrado con username "<username>"
    When  el actor consulta los datos del usuario "<username>" mediante GET
    Then  el username del usuario devuelto es "<username>"

    Examples:
      | username              |
      | get_user_screenplay_1 |