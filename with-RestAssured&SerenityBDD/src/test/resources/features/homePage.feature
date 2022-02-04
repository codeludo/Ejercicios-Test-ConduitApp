Feature: not user home page view
  As Donatelo is not user in conduit app
  He want see global feed request
  to review that only three articles are shown.

  Background:
    Given that 'Donatelo' can request conduit app rest services

  Scenario:
    When he request the global feed in conduit home page
    Then he only can see three articles