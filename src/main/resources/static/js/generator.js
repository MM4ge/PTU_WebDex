const movesElem = document.getElementById("Moves");
const abilityElem = document.getElementById("Abilities");
var pokeMovesAry = [];
console.log("Page Load");
setupAutoMoves();

// TODO: either make the pokemon species / form immutable or save the originals on page load and display a warning
//      that the user will have to regen the poke with submit if they made changes to it to see them

function alertThing(thing) {
    console.log("Hello World" + thing);
    addBlankMove();
//    pokeMovesAry = JSON.parse('${pokemonMoves}');
    setupAutoMoves();
    console.log("after call");
//    getPossibleMoves();
}

//function addMoveSearch(moveNum) {
//    var move_list = 'noValue';
//    $(document).ready(function() {
//    $("#move[${moveNum}]").autocomplete({
//        minLength : 0,
//        source : move_list,
//        })
//    });
//}
function autoMovesHelper(elem) {
    $(document).ready(function() {
        $(elem).autocomplete({
            source : function(request, resolve) {
                // fetch new values with request.term
                resolve(pokeMovesAry);
            },
            minLength : 0
        })
    });
}

function setupAutoMoves() {
    var moves = movesElem.children.length - 1;
    getPossibleMoves();
    for (var i = 0; i < moves; i++) {
        console.log("Setup on run move #" + i);
        var currMove = document.getElementById("move" + i);
        console.log(currMove);
        autoMovesHelper(currMove);
    }
}

function getPossibleMoves() {
    var name = document.getElementById("pokeName").value;
    var form = document.getElementById("pokeForm").value;
    var level = document.getElementById("pokeLevel").value;
    // get move / ability from endpoint
    fetch("http://localhost:8081/api/pokeMoves?name=" + name + "&form=" + form + "&level=" + level)
      .then(response => response.json())
      .then(data => {
        pokeMovesAry = data;
        console.log(pokeMovesAry);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
}

function addBlankMove() {
    event.preventDefault();
    // moveNum is 1 more than # of moves, due to the header, subtract 1 as moves starts at 0
    var moveNum = movesElem.childElementCount - 1;
    var div = document.createElement("div");
    div.setAttribute("class", "border border-dark rounded m-1 p-2");
    div.innerHTML =
        `
        <!-- Name, Move Class, Frequency -->
        <div class="d-flex flex-row justify-content-between border-bottom">
            <div class="d-flex flex-column">
                <p class="weight-light m-0" style="font-size: small"><i>Name:</i></p>
                <input class="form-short rounded" id="move${moveNum}" type="text" name="moves[${moveNum}].name" autocomplete = "off"/>
            </div>
            <div class="d-flex flex-column">
                <p class="weight-light m-0" style="font-size: small"><i>Class:</i></p>
                <div id="moveClass"></div>
            </div>
            <div class="d-flex flex-column">
                <p class="weight-light m-0" style="font-size: small"><i>Frequency:</i></p>
                <div id="moveFreq"></div>
            </div>
        </div>
        <!-- Display Name, Range, AC, DB -->
        <div class="d-flex flex-row justify-content-between">
            <div class="d-flex flex-column">
                <p class="weight-light m-0" style="font-size: small"><i>Type:</i></p>
                <div id="moveType"></div>
            </div>
            <div class="d-flex flex-column">
                <p class="weight-light m-0" style="font-size: small"><i>Range:</i></p>
                <div id="moveRange"></div>
            </div>
            <div class="d-flex flex-column">
                <p class="weight-light m-0" style="font-size: small"><i>AC:</i></p>
                <div id= "moveAC"></div>
            </div>
            <div class="d-flex flex-column">
                <p class="weight-light m-0" style="font-size: small"><i>DB:</i></p>
                <div id="moveDB"></div>
            </div>
        </div>
    `;
    movesElem.appendChild(div);
    /*
            <!-- Effect -->
            <div class="d-flex flex-column border-top">
                <p class="weight-light m-0" style="font-size: small"><i>Effect:</i></p>
                <div id="moveEffect"></div>
            </div>
    */
}

