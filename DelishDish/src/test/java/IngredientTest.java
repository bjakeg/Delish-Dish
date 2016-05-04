import com.parse.starter.Ingredient;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jakegraham on 4/30/16.
 */

public class IngredientTest {

    @Test
    public void testCreateRecipe() {
        Ingredient ingredient = new Ingredient("title", "amount", 3);
        assertEquals(ingredient.getTitle(), "title");
        assertEquals(ingredient.getAmount(), "amount");
        assertEquals(ingredient.getQuantity(), 3);
    }
}
