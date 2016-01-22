package common.core;

/**
 * Created by Sahidul Islam.
 */
public class MouseState {
    private Vector2 mousePosition = Vector2.getZero();
    private Vector2 leftClickPosition = Vector2.getZero();
    private Vector2 rightClickPosition = Vector2.getZero();
    private Sprite selectedSprite = null;

    public Vector2 getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(double x, double y) {
        this.mousePosition.setXY(x, y);
    }

    public Vector2 getLeftClickPosition() {
        return leftClickPosition;
    }

    public void setLeftClickPosition(double x, double y) {
        this.leftClickPosition.setXY(x, y);
    }

    public Vector2 getRightClickPosition() {
        return rightClickPosition;
    }

    public void setRightClickPosition(double x, double y) {
        this.rightClickPosition.setXY(x, y);
    }

    public Sprite getSelectedSprite() {
        return selectedSprite;
    }

    public <T extends Sprite> T getSelectedSprite(Class<T> type) {
        if (this.selectedSprite != null && type.isInstance(this.selectedSprite))
            return type.cast(this.selectedSprite);

        return null;
    }

    public void clearSelectedSprite() {
        if (this.selectedSprite != null) {
            this.selectedSprite = null;
        }
    }

    public void setSelectedSprite(Sprite selectedSprite) {
        this.selectedSprite = selectedSprite;
    }
}
