package rcp3.study.viewers;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use ComboViewer.
 * 
 * @author Alex
 */
public class ComboViewerUsage implements ShellRunner {
	
	public static void main(String[] args) {
		new ComboViewerUsage().openShell();
	}

	@Override
	public void fillContent(Composite parent) {
		ComboViewer comboViewer = new ComboViewer(parent, SWT.NONE);
		comboViewer.setContentProvider(new ArrayContentProvider());

		comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return Display.getDefault().getSystemImage(SWT.ICON_INFORMATION);
			}

			@Override
			public String getText(Object element) {
				Student st = (Student) element;
				return String.format("%s - %s", st.getName(), st.getCountry());
			}
		});

		List<Student> students = StudentFactory.tableInput();
		comboViewer.setInput(students);
		comboViewer.setSelection(new StructuredSelection(students.get(0)));

		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				Object firstElement = selection.getFirstElement();
				if (firstElement instanceof Student) {
					// Do something.
				}
			}
		});
		
		comboViewer.getCombo().addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				System.out.println("Input changed.");
			}
		});

		comboViewer.getCombo().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (SWT.CR == e.keyCode) {
					System.out.println("Entery key is pressed.");
				}
			}
		});

		comboViewer.getCombo().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Focus is lost.");
			}
		});

		comboViewer.getCombo().addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				// Prevent from inputing letter "s".
				e.doit = !"s".equals(e.text);
			}
		});
		
	}

}
