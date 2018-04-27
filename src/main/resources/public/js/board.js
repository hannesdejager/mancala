function updateBoard(game) {
    $('.mancala[data-locality=west] .stone-count').text(game.board.northSection.mancala.stoneCount);
    $('.mancala[data-locality=east] .stone-count').text(game.board.southSection.mancala.stoneCount);
    $('.pit[data-locality=north]').each(function() {
        var index = $(this).attr('data-index');
        $(this).text(game.board.northSection.pits[index].stoneCount);
    });
    $('.pit[data-locality=south]').each(function() {
        var index = $(this).attr('data-index');
        $(this).text(game.board.southSection.pits[index].stoneCount);
    });
    $('.player-name').removeClass('current-player');
    $('.player-name[data-player-index='+game.currentPlayerId+']').addClass('current-player');
    if (game.currentPlayerId == 0) {
        $('#south-pits button').attr("disabled", "disabled");
        $('#north-pits button').removeAttr("disabled");
    } 
    else {
        $('#north-pits button').attr("disabled", "disabled");
        $('#south-pits button').removeAttr("disabled");
    }    
}

function makeMove(pitIndex) {    
    var moveObj = { 'pitIndex' : pitIndex };
    $.ajax({
        url: "/",
        method: 'PATCH',
        dataType: 'game',
        data: JSON.stringify(moveObj),
        contentType: 'application/json',
        accepts: {
            game: 'application/vnd.cloudinvoke-mancala.game+json'
        },
        converters: {
            'text game': jQuery.parseJSON
        }
    }).done(function(game, textStatus, jqXHR) {
        updateBoard(game);
    }).fail(handleFailedAjax);        
}

function handleFailedAjax(jqXHR, error, errorThrown) {
    if (jqXHR.status && jqXHR.status == 400) {
        var msgObj = JSON.parse(jqXHR.responseText);
        alert(msgObj.message); 
    } else{
        alert("Something went wrong: " + error);
    }
}

function loadGame() {
    $.ajax({
        url: "/",
        method: 'GET',
        dataType: 'game',
        accepts: {
            game: 'application/vnd.cloudinvoke-mancala.game+json'
        },
        converters: {
            'text game': jQuery.parseJSON
        }
    }).done(function(game, textStatus, jqXHR) {
        updateBoard(game);
    }).fail(handleFailedAjax);   
}

$(document).ready(function() {
    loadGame();

    $('#pits button').click(function() {
            var pitIndex = $(this).attr('data-index');
            makeMove(pitIndex);        
    });
});
