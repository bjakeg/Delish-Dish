import com.parse.starter.Ingredient;
import com.parse.starter.Recipe;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by jakegraham on 4/30/16.
 */
public class RecipeTest {

    @Test
    public void testCreateRecipe() {
        Recipe recipe = new Recipe("title", "link", "cat", null, null);
        assertNull(recipe.getIngredients());
        assertNull(recipe.getInstructions());
        assertEquals(recipe.getTitle(), "title");
        assertEquals(recipe.getImageLink(), "link");
        assertEquals(recipe.getCategory(), "cat");
    }


    @Test
    public void testAddIngredient() throws Exception {
        Recipe recipe = new Recipe("title", "link", "cat", null, null);
        Ingredient ingredient = new Ingredient("t", "a", 2);
        recipe.addIngredient(ingredient);
        List<Ingredient> expectedList = new ArrayList<Ingredient>();
        expectedList.add(ingredient);
        assertEquals(recipe.getIngredients(), expectedList);
    }

    @Test
    public void testAddInstruction() throws Exception {
        Recipe recipe = new Recipe("title", "link", "cat", null, null);
        recipe.addInstruction("Instruction");
        List<String> expectedList = new ArrayList<String>();
        expectedList.add("Instruction");
        assertEquals(recipe.getInstructions(), expectedList);
    }

}

