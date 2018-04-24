<!DOCTYPE html>
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="js/board.js"></script>
    </head>
    <body>

        <div id="board">
            <div class='mancala' data-locality="west"><span class='player-name current-player' data-player-index='0'>${playerA}</span><span class='stone-count'>${mancalaACount}</span></div>
            <div id="pits">
                    <div class='pit-row' id="north-pits">
                        <#list northPits as pit>
                            <button class='pit' data-locality="north" data-index="${pit?index}">${pit.stoneCount}</button>
                        </#list>
                    </div>
                    <div id="south-pits" class='pit-row'>
                        <#list southPits as pit>
                            <button class='pit' data-locality="south" data-index="${pit?index}">${pit.stoneCount}</button>
                        </#list>
                    </div>                        
            </div>
            <div class='mancala' data-locality="east"><span class='player-name' data-player-index='1'>${playerB}</span><span class='stone-count'>${mancalaBCount}</span></div>
        </div>

    </body>
</html>