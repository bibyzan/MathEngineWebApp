<h1>${message}</h1>
<input id= "input" type="text" name="input" />
<div>
    <button class="number-input">1</button>
    <button class="number-input">2</button>
    <button class="number-input">3</button>
</div>
<div>
    <button class="number-input">4</button>
    <button class="number-input">5</button>
    <button class="number-input">6</button>
</div>
<div>
    <button class="number-input">7</button>
    <button class="number-input">8</button>
    <button class="number-input">9</button>
</div>
<div>
    <button class="btn-calculate">Calculate</button>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.js"></script>
<script>
    // Append the html of a button to the input's value
    $('.number-input').on('click', function() {
        $('#input').val($('#input').val() + $(this).html())
    });

    // When the calculate button is clicked
    // send a JSON request to "/calculate"
    // with the equation
    $('.btn-calculate').on('click', function() {
        // Define the data
        data = {
            equation: $('#input').val()
        };

        // Make the request
        $.post('/calculate', data, function(response) {
            // Display the response
            $('#input').val(response.equation)
        });
    });
</script>