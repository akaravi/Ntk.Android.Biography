package ntk.android.biography;

import java.util.ArrayList;
import java.util.List;

import ntk.android.biography.model.theme.Drawer;
import ntk.android.biography.model.theme.DrawerChild;
import ntk.android.biography.model.theme.HamberMenu;
import ntk.android.biography.model.theme.SearchBox;
import ntk.android.biography.model.theme.ShoppingCart;
import ntk.android.biography.model.theme.Theme;
import ntk.android.biography.model.theme.Toolbar;

public class JsonCreator {
    public static Theme DRAWER() {

        Drawer drawer = new Drawer();
        drawer.CircleImage = null;
        drawer.HeaderImage = "https://wikifamous.com/wp-content/uploads/2018/06/Messi-HD-Wallpaper-1200x630.jpg?x50236";
        drawer.Type = 1;
        List<DrawerChild> childs = new ArrayList<>();
        childs.add(new DrawerChild(1, 1, "صندوق پیام", "https://image.flaticon.com/icons/png/512/107/107822.png"));
        childs.add(new DrawerChild(2, 2, "اخبار", "https://image.flaticon.com/icons/png/512/31/31866.png"));
        childs.add(new DrawerChild(3, 2, "نظرسنجی", "https://image.flaticon.com/icons/png/512/120/120114.png"));
        childs.add(new DrawerChild(5, 2, "دعوت از دوستان", "https://image.flaticon.com/icons/png/512/109/109534.png"));
        childs.add(new DrawerChild(6, 2, "درباره ما", "https://image.flaticon.com/icons/png/512/97/97849.png"));
        childs.add(new DrawerChild(7, 2, "تماس با ما", "https://image.flaticon.com/icons/png/512/13/13936.png"));
        childs.add(new DrawerChild(8, 2, "بازخورد", "https://image.flaticon.com/icons/png/512/87/87702.png"));
        childs.add(new DrawerChild(9, 2, "پرسش های متداول", "https://image.flaticon.com/icons/png/512/43/43392.png"));
        childs.add(new DrawerChild(10, 2, "وبلاگ", "https://image.flaticon.com/icons/png/512/31/31866.png"));
        drawer.Child = childs;

//        HmberMenu
        HamberMenu HM = new HamberMenu();
        HM.Color = "#00796B";
        HM.Image = "https://image.flaticon.com/icons/png/512/52/52045.png";

//        SearchBox
        SearchBox SB = new SearchBox();
        SB.Color = "#00796B";
        SB.Image = "https://image.flaticon.com/icons/png/512/55/55369.png";

//        ShoppingCart
        ShoppingCart cart = new ShoppingCart();
        cart.Color = "#00796B";
        cart.Image = "https://image.flaticon.com/icons/png/512/107/107831.png";

//        Toolbar
        Toolbar toolbar = new Toolbar();
        toolbar.Cart = cart;
        toolbar.Type = 1;
        toolbar.BackgroundColor = "#ffffff";
        toolbar.ColorBelowLine = "#00796B";
        toolbar.Drawer = drawer;
        toolbar.HamberMenu = HM;
        toolbar.SearchBox = SB;

        Theme theme = new Theme();
        theme.Toolbar = toolbar;

        return theme;
    }
}
