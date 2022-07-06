# PTU Viewer / WebDex
## Project Description
This is a small website that allows users to view data from the Pokemon Tabletop United Core Rulebook and Pokedex online, instead of having to deal with 1k+ pages of PDFs.
The website's CSS Template layout is Bare - Bootstrap v5.1.3 (https://startbootstrap.com/template/bare).

## Viewing Data
### General Description
Moves and Abilities can easily be viewed or even searched for on the site; go to the dropdown menu in the upper right for whichever datatype you want to view and select "See All". The website will provide a table with additional page navigation on the bottom. If you select "Search For", you'll be presented a form to fill out of what information in your datatype you want to find. If you search for a name, it'll only search by name and only for exact matches. Otherwise, any datatype that matches what was entered in the form will be presented on a results page.
### Technical Description
- Abilities and Moves can be read by their entire tables with pagination, or with subsections of searchs via example matching.
- When visiting a "See All" page, the database retrieves the first page of entities from the database, and Spring uses Pages / Pagination to generate links at the bottom for the other pages, if there are any more.
- Abilities and Moves data to search for are supplied to/from the user's form via AbilityDTO and MoveDTO respectively to prevent malicious code injection.
- The DTOs are then used to search the database via an ExampleMatcher, to select any entity that has all of the user-supplied criteria.
- Matches are shown on the same results page as "See All" but without pagination.

## Create/Update/Delete
### General Description
If you want to Create, Update, or Delete a Move, the dropdown menu on the upper right for Moves has two additional options; "Create or Update a Move" and "Delete a Move". Create/Update allows you to fill in all the Move information you wish, and creates or updates moves based on if the move's name is already in the database (i.e. that move did or didn't already exist). Delete only asks you for the move's name, and deletes it if that exact name is in the database. Errors in either of the forms (like if you tried to delete a non-existing move) will return you to the form and show a red error message stating what's wrong. Upon successfully completing an action, a small JavaScript alert will show up to tell you that your action was successful and the results page will show the new/old move that was acted on.
### Technical Description
- Users may Create, Update, and Delete moves from the Database. These changes are only stored while the program is running, as Spring drops the database entirely once it closes.
- The Create/Update pages uses DTOs like search does, and creates a new entity to save to the database if there aren't any errors in the form.
- If there are errors, like if the name field is blank or all drop-down enum menus don't have a selection, the user is returned back to the form and error messages are shown to them to show what they need to do.
- The new/updated entity is shown to the user on a results page, along with a pop-up JS alert to show that the action succeeded.
- Delete also uses a DTO for safety, but the form only has the one input field for the entity's name.
- Similarly to Create/Update, if a deletion is successful, the deleted entity is shown to the user and a popup confirms the action's success. If there's an error, the user is returned to the form to fix the error.
