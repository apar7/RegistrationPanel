

function loadWeightEdit(waga, kategoria) {
    var input;
    switch (kategoria) {
        case 'juniorzy':
            input = "<option value=-51>-51</option>" +
                "<option value=-56>-56</option>" +
                "<option value=-61>-61</option>" +
                "<option value=+61>+61</option>";
            break;
        case 'seniorzy':
            input = "<option value=-64>-64</option>" +
                "<option value=-70>-70</option>" +
                "<option value=-76>-76</option>" +
                "<option value=-82,3>-82,3</option>" +
                "<option value=-88,3>-88,3</option>";
            break;
        case 'kobiety':
            input = "<option value=-50>-50</option>" +
                "<option value=+50>+50</option>";
            break;
    }
    waga.append(input);
}