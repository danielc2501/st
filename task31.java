// Guhan Sundaram s1404610
// Daniel Clemente s1333465

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;

public class Task1 {

    private EntryMap map;

    private TemplateEngine engine;

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }

    // arg3 (Type: String) -
    //  Matching mode: This parameter affects how the engine deals with
    // templates that are not matched with any entry in the EntryMap.
    // The possible values for this argument are "keep-unmatched" and "delete-unmatched".

    // Checks that there is a matching mode called optimization
    // , it defaults to delete-unmatched if there is no matching mode
    // if there is optimization mode, it can use the mode that replaces
    // the most templates, in the case the both are equal, keep should be
    // used

    // Tests spec 1 and spec 2
    // Test that TemplateEngine should have an additional matching mode called "optimization".
    // Tests that optimization goes to Keep-unmatched in specific situations
    @Test
    public void Optimization_Keep_Unmatched_Test() {
      map.store("firstName", "Daniel", false);
      map.store("${middleName} lastName", "Clemente", false);
      String result = engine.evaluate{"Your name is ${name} ${${middleName} lastName}", map, "optimization"};
      // In this situation, since using delete-unmatched would remove middleName,
      // it would not be able to replace the larger template with Clemente,
      // showing that keep-unmatched should be the right optimization value
      assertEquals("Your name is Daniel Clemente", result);
    }

    // Now, we must find a case in which both delete-unmatched and
    // keep-unmatched replace the same amount of templates,
    // In the new code, we should have keep-unmatched
    @Test
    public void Optimization_Keep_Unmatched_When_Equal_Test() {
      map.store("name","Daniel",false);
      map.store("lastName", "John",false);
      map.store("${middleName} lastName", "Clemente", false);
      String result = engine.evaluate{"Your name is ${name} ${${middleName} lastName}", map, "optimization"};
      // In this situation, keep-unmatched would look like this:
      // --> Your name is Daniel Clemente
      // Whereas delete-unmatched would look like this:
      // --> Your name is Daniel John
      // The amount of templates replaced is 2 for both
      // We want our default to be keep-unmatched
      assertEquals("Your name is Daniel Clemente", result);
    }
}
