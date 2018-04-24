/**
 * This package contains Data Transfer Objects (DTOs) for the Mancala game. The {@link com.cloudinvoke.mancala.dto.Game Game} class is the top level structure. These
 * DTOs are serialized to JSON and gets sent to the REST client. They contain zero business logic. All fields are public and getters/setters are omitted where possible 
 * to clarify their intent (data structures not objects) and to make code more readable plus make it obvious that their use is not a violation of the 
 * Law of <a href="https://en.wikipedia.org/wiki/Law_of_Demeter">Demeter.</a>
 */
package com.cloudinvoke.mancala.dto;