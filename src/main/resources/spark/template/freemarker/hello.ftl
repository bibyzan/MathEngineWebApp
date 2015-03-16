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
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.js"></script>
<script>
    $('.number-input').on('click', function() {
        $('#input').val($('#input').val() + $(this).html())
    });
</script>