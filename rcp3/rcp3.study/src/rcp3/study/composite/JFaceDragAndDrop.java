package rcp3.study.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use JFace's drag and drop.
 * 
 * @author Alex
 */
public class JFaceDragAndDrop implements ShellRunner {

  public static void main(String[] args) {
    new JFaceDragAndDrop().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    parent.setLayout(new FormLayout());
    parent.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

    final Label dragLabel = new Label(parent, SWT.BORDER);
    dragLabel.setText("Text to be transferred.");

    FormData fd = new FormData();
    fd.top = new FormAttachment(0, 60);
    fd.left = new FormAttachment(0, 20);
    dragLabel.setLayoutData(fd);

    DragSource source = new DragSource(dragLabel, DND.DROP_MOVE);

    Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
    source.setTransfer(types);

    source.addDragListener(new DragSourceAdapter() {
      Image dragSourceImage;
      int xOffset;
      int yOffset;

      @Override
      public void dragStart(DragSourceEvent event) {
        super.dragStart(event);
        // Remember offset.
        xOffset = event.x;
        yOffset = event.y;

        // Make sure image just start from Label position.
        event.offsetX = event.x;
        event.offsetY = event.y;
        event.image = createImage();
      }

      private Image createImage() {
        if (dragSourceImage != null && !dragSourceImage.isDisposed()) {
          dragSourceImage.dispose();
        }
        Point size = dragLabel.getSize();
        // Create an empty image.
        dragSourceImage = new Image(Display.getDefault(), size.x, size.y);

        GC gc = new GC(dragLabel);
        gc.copyArea(dragSourceImage, 0, 0);
        gc.dispose();

        return dragSourceImage;
      }

      @Override
      public void dragSetData(DragSourceEvent event) {
        if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
          event.data = String.join(",", String.valueOf(xOffset), String.valueOf(yOffset));
        }
      }

      @Override
      public void dragFinished(DragSourceEvent event) {
        super.dragFinished(event);
        if (dragSourceImage != null && !dragSourceImage.isDisposed()) {
          dragSourceImage.dispose();
        }
      }
    });

    DropTarget target = new DropTarget(parent, DND.DROP_MOVE);
    target.setTransfer(types);

    target.addDropListener(new DropTargetAdapter() {
      public void drop(DropTargetEvent event) {
        String[] offsets = event.data.toString().split(",");
        int xOffset = Integer.parseInt(offsets[0]);
        int yOffset = Integer.parseInt(offsets[1]);

        Point location = parent.toControl(event.x - xOffset, event.y - yOffset);
        fd.left = new FormAttachment(0, location.x);
        fd.top = new FormAttachment(0, location.y);

        parent.layout();
      }
    });
  }

}
