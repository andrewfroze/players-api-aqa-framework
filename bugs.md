1. ***Swagger endponts have bad/wrong methods and descriptions***

actual: GET /player/create/{editor} - createPlayer
expected: POST /player/create/{editor} - createPlayer

actual: POST /player/get - getPlayerByPlayerId
actual works, but better to have something like: GET /player/{playerId}

same for delete: DELETE /player/delete/{playerId}

I'm not sure that {editor} parameter is needed somewhere. Editor id better include to the JWT token.

2. ***Get all players endpoint returns password of user as a string (including supervisor)***

3. ***Create player endpoint***
response status code 200 instead of 201
response body contains only id and login non-null values

4. ***Create player: 400 sc for age=16 (should be valid)***

5. ***Create player: age=60 - user is created, but should not be***

6. ***Player can be created with any password, including missing or empty***

7. ***Player can be created with non-unique screen name***

8. ***User could be created with any non-empty gender***

9. ***Get player by id returns body with id for invalid and non-existent ids***

10. ***Role is not updated on player update***

11. ***Player can be updated with non-unique screenName***

12. ***User could be updated with any non-empty gender***

13. ***Age can be updated to not allowed one***

14. ***Strange status code 403 on delete non-existent player. better 404***