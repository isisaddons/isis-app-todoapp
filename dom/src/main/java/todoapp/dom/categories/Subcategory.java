package todoapp.dom.categories;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;

public enum Subcategory {
    // professional
    OPEN_SOURCE, CONSULTING, EDUCATION, MARKETING,
    // domestic
    SHOPPING, HOUSEWORK, GARDEN, CHORES,
    // other
    OTHER;

    public static List<Subcategory> listFor(final Category category) {
        return category != null? category.subcategories(): Collections.<Subcategory>emptyList();
    }

    public static String validate(final Category category, final Subcategory subcategory) {
        if(category == null) {
            return "Enter category first";
        }
        return !category.subcategories().contains(subcategory)
                ? "Invalid subcategory for category '" + category + "'"
                : null;
    }

    public static Predicate<Subcategory> thoseFor(final Category category) {
        return new Predicate<Subcategory>() {

            @Override
            public boolean apply(final Subcategory subcategory) {
                return category.subcategories().contains(subcategory);
            }
        };
    }

}
