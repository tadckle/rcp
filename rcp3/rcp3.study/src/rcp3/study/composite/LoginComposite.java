package rcp3.study.composite;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp3.study.resource.SWTImgResource;

/**
 * Use Form to decorate a login composite.
 * 
 * @author Alex
 */
public class LoginComposite extends Composite {

  private final FormToolkit toolkit = new FormToolkit(Display.getDefault());

  private Form loginForm;

  public LoginComposite(Composite parent, int style) {
    super(parent, style);
    this.setLayout(new FillLayout());

    loginForm = toolkit.createForm(this);
    loginForm.setText("Login System");
    loginForm.setImage(SWTImgResource.LOGIN);
    loginForm.setSeparatorVisible(true);
    toolkit.decorateFormHeading(loginForm);

    Composite headClient = new Composite(loginForm.getHead(), SWT.NONE);
    GridLayoutFactory.swtDefaults().applyTo(headClient);
    Label headClientLbl = new Label(headClient, SWT.NONE);
    headClientLbl.setText("A great ship asks for deep waters.");
    GridDataFactory.swtDefaults().applyTo(headClientLbl);
    loginForm.setHeadClient(headClient);

    addActions(loginForm);

    loginForm.addTitleDragSupport(DND.DROP_MOVE, new Transfer[] { TextTransfer.getInstance() },
        new DragSourceAdapter() {
          @Override
          public void dragSetData(DragSourceEvent event) {
            event.data = "Drag data";
          }
        });

    loginForm.addMessageHyperlinkListener(new HyperlinkAdapter() {
      @Override
      public void linkActivated(HyperlinkEvent e) {
        MessageBox msgBox = new MessageBox(LoginComposite.this.getShell(), SWT.ICON_INFORMATION);
        msgBox.setText("Login failed");
        msgBox.setMessage("If you forgot your password. " + "Please consult our INFO DESK to restore your password.");
        msgBox.open();
      }
    });

    createLoginWidgets(loginForm.getBody());
  }

  private void addActions(Form loginForm) {
    IToolBarManager toolBarManager = loginForm.getToolBarManager();
    IMenuManager menuManager = loginForm.getMenuManager();

    Action addAction = new Action() {
      @Override
      public void run() {
        // TODO: Add something
        super.run();
      }
    };
    addAction.setImageDescriptor(ImageDescriptor.createFromImage(SWTImgResource.ADD));
    addAction.setText("Add");
    addAction.setToolTipText("Add something");
    menuManager.add(addAction);
    toolBarManager.add(addAction);

    Action likeAction = new Action() {
      @Override
      public void run() {
        // TODO: Add to favorite
        super.run();
      }
    };
    likeAction.setImageDescriptor(ImageDescriptor.createFromImage(SWTImgResource.HEART));
    likeAction.setText("Like");
    likeAction.setToolTipText("Add to favorite");
    menuManager.add(likeAction);
    toolBarManager.add(likeAction);

    menuManager.update(true);
    toolBarManager.update(true);
  }

  private void createLoginWidgets(Composite parent) {
    GridLayoutFactory.fillDefaults().applyTo(parent);

    Composite loginComp = toolkit.createComposite(parent);
    GridDataFactory.fillDefaults().grab(true, false).applyTo(loginComp);
    GridLayoutFactory.swtDefaults().numColumns(2).applyTo(loginComp);

    Label idLbl = toolkit.createLabel(loginComp, "User ID");
    GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(idLbl);

    Text idTxt = toolkit.createText(loginComp, "", SWT.BORDER);
    GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(idTxt);

    // Add drag accept behavior.
    DropTarget dropTarget = new DropTarget(idTxt, DND.DROP_MOVE);
    dropTarget.setTransfer(new Transfer[] { TextTransfer.getInstance() });
    dropTarget.addDropListener(new DropTargetAdapter() {
      public void drop(DropTargetEvent event) {
        idTxt.setText((String) event.data);
      }
    });

    Label pwdLbl = toolkit.createLabel(loginComp, "Password");
    GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(pwdLbl);

    Text pwdTxt = toolkit.createText(loginComp, "", SWT.BORDER);
    GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(pwdTxt);

    Button loginBtn = toolkit.createButton(loginComp, "Login", SWT.NONE);
    GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).grab(true, false).span(2, 1).applyTo(loginBtn);

    loginBtn.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (!"alex".equals(idTxt.getText()) || !"alex".equals(pwdTxt.getText())) {
          // loginForm.setMessage("This information message",
          // IMessageProvider.INFORMATION);
          // loginForm.setMessage("This warning message", IMessageProvider.WARNING);
          loginForm.setMessage("User id or password are incorrect.", IMessageProvider.ERROR);
          loginForm.setBusy(true);
        } else {
          loginForm.setMessage(null);
        }
      }
    });
  }

}
