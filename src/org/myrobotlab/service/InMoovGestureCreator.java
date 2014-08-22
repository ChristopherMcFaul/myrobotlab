package org.myrobotlab.service;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.myrobotlab.framework.Service;
import org.myrobotlab.logging.Level;
import org.myrobotlab.logging.LoggerFactory;
import org.myrobotlab.logging.Logging;
import org.myrobotlab.logging.LoggingFactory;
import org.myrobotlab.service.Python.Script;
import org.slf4j.Logger;

/**
 * based on _TemplateService
 */
/**
 *
 * @author LunDev (github), Ma. Vo. (MyRobotlab)
 */
public class InMoovGestureCreator extends Service {

	private static final long serialVersionUID = 1L;
	public final static Logger log = LoggerFactory
			.getLogger(InMoovGestureCreator.class);

	ServoItemHolder[][] servoitemholder;
	ArrayList<FrameItemHolder> frameitemholder;

	boolean[] tabs_main_checkbox_states;

	boolean moverealtime = false;

	InMoov i01;

	public InMoovGestureCreator(String n) {
		super(n);
		// intializing variables
		servoitemholder = new ServoItemHolder[6][];
		frameitemholder = new ArrayList<FrameItemHolder>();

		tabs_main_checkbox_states = new boolean[6];

		Python python = (Python) Runtime.getService("python");
		Script script = python.getScript();
		String code = script.getCode();
		System.out.println(code);
	}

	@Override
	public void startService() {
		super.startService();
	}

	@Override
	public void stopService() {
		super.stopService();
	}

	@Override
	public String getDescription() {
		return "an easier way to create gestures for InMoov";
	}

	public void tabs_main_checkbox_states_changed(
			boolean[] tabs_main_checkbox_states2) {
		// checkbox states (on the main site) (for the services) changed
		tabs_main_checkbox_states = tabs_main_checkbox_states2;
	}

	public void control_connect(JButton control_connect) {
		// Connect / Disconnect to / from the InMoov service (button bottom-left)
		if (control_connect.getText().equals("Connect")) {
			i01 = (InMoov) Runtime.getService("i01");
			control_connect.setText("Disconnect");
		} else {
			i01 = null;
			control_connect.setText("Connect");
		}
	}

	public void control_load() {
		//Load the current Python-Script for editing (out Python-Service) (button bottom-left)
		//TODO - add functionality
	}

	public void control_save() {
		//Save the current Python-Script (in Python-Service) (button bottom-left)
		//TODO - add functionality
	}

	public void control_add() {
		//Add the current gesture to the script (button bottom-left)
		//TODO - add functionality
	}
	
	public void control_update() {
		//Update the current gesture in the script (button bottom-left)
		//TODO - add functionality
	}

	public void control_remove() {
		//Remove the selected gesture from the script (button bottom-left)
		//TODO - add functionality
	}

	public void control_testgest() {
		// test (execute) the created gesture (button bottom-left)
		if (i01 != null) {
			for (FrameItemHolder fih : frameitemholder) {
				if (fih.sleep != -1) {
					sleep(fih.sleep);
				} else if (fih.speech != null) {
					i01.mouth.speakBlocking(fih.speech);
				} else if (fih.name != null) {
					if (tabs_main_checkbox_states[0]) {
						i01.moveHead(fih.neck, fih.rothead, fih.eyeX, fih.eyeY,
								fih.jaw);
					}
					if (tabs_main_checkbox_states[1]) {
						i01.moveArm("left", fih.lbicep, fih.lrotate,
								fih.lshoulder, fih.lomoplate);
					}
					if (tabs_main_checkbox_states[2]) {
						i01.moveArm("right", fih.rbicep, fih.rrotate,
								fih.rshoulder, fih.romoplate);
					}
					if (tabs_main_checkbox_states[3]) {
						i01.moveHand("left", fih.lthumb, fih.lindex,
								fih.lmajeure, fih.lringfinger, fih.lpinky,
								fih.lwrist);
					}
					if (tabs_main_checkbox_states[4]) {
						i01.moveHand("right", fih.rthumb, fih.rindex,
								fih.rmajeure, fih.rringfinger, fih.rpinky,
								fih.rwrist);
					}
					if (tabs_main_checkbox_states[5]) {
						i01.moveTorso(fih.topStom, fih.midStom, fih.lowStom);
					}
				} else {
					if (tabs_main_checkbox_states[0]) {
						i01.setHeadSpeed(fih.neckspeed, fih.rotheadspeed,
								fih.eyeXspeed, fih.eyeYspeed, fih.jawspeed);
					}
					if (tabs_main_checkbox_states[1]) {
						i01.setArmSpeed("left", fih.lbicepspeed,
								fih.lrotatespeed, fih.lshoulderspeed,
								fih.lomoplatespeed);
					}
					if (tabs_main_checkbox_states[2]) {
						i01.setArmSpeed("right", fih.rbicepspeed,
								fih.rrotatespeed, fih.rshoulderspeed,
								fih.romoplatespeed);
					}
					if (tabs_main_checkbox_states[3]) {
						i01.setHandSpeed("left", fih.lthumbspeed,
								fih.lindexspeed, fih.lmajeurespeed,
								fih.lringfingerspeed, fih.lpinkyspeed,
								fih.lwristspeed);
					}
					if (tabs_main_checkbox_states[4]) {
						i01.setHandSpeed("right", fih.rthumbspeed,
								fih.rindexspeed, fih.rmajeurespeed,
								fih.rringfingerspeed, fih.rpinkyspeed,
								fih.rwristspeed);
					}
					if (tabs_main_checkbox_states[5]) {
						i01.setTorsoSpeed(fih.topStomspeed, fih.midStomspeed,
								fih.lowStomspeed);
					}
				}
			}
		}
	}

	// TODO - this is not used any longer - REUSE it!
	public void exportcode(JTextArea generatedcode) {
		// export the code for using it in a InMoov gesture (button bottom-left)
		String code = "";
		for (FrameItemHolder fih : frameitemholder) {
			String code1;
			if (fih.sleep != -1) {
				code1 = "sleep(" + fih.sleep + ")\n";
			} else if (fih.speech != null) {
				code1 = "i01.mouth.speakBlocking(\"" + fih.speech + "\")\n";
			} else if (fih.name != null) {
				String code11 = "";
				String code12 = "";
				String code13 = "";
				String code14 = "";
				String code15 = "";
				String code16 = "";
				if (tabs_main_checkbox_states[0]) {
					code11 = "i01.moveHead(" + fih.neck + "," + fih.rothead
							+ "," + fih.eyeX + "," + fih.eyeY + "," + fih.jaw
							+ ")\n";
				}
				if (tabs_main_checkbox_states[1]) {
					code12 = "i01.moveArm(\"left\"," + fih.lbicep + ","
							+ fih.lrotate + "," + fih.lshoulder + ","
							+ fih.lomoplate + ")\n";
				}
				if (tabs_main_checkbox_states[2]) {
					code13 = "i01.moveArm(\"right\"," + fih.rbicep + ","
							+ fih.rrotate + "," + fih.rshoulder + ","
							+ fih.romoplate + ")\n";
				}
				if (tabs_main_checkbox_states[3]) {
					code14 = "i01.moveHand(\"left\"," + fih.lthumb + ","
							+ fih.lindex + "," + fih.lmajeure + ","
							+ fih.lringfinger + "," + fih.lpinky + ","
							+ fih.lwrist + ")\n";
				}
				if (tabs_main_checkbox_states[4]) {
					code15 = "i01.moveHand(\"right\"," + fih.rthumb + ","
							+ fih.rindex + "," + fih.rmajeure + ","
							+ fih.rringfinger + "," + fih.rpinky + ","
							+ fih.rwrist + ")\n";
				}
				if (tabs_main_checkbox_states[5]) {
					code16 = "i01.moveTorso(" + fih.topStom + "," + fih.midStom
							+ "," + fih.lowStom + ")\n";
				}
				code1 = code11 + code12 + code13 + code14 + code15 + code16;
			} else {
				String code11 = "";
				String code12 = "";
				String code13 = "";
				String code14 = "";
				String code15 = "";
				String code16 = "";
				if (tabs_main_checkbox_states[0]) {
					code11 = "i01.setHeadSpeed(" + fih.neckspeed + ","
							+ fih.rotheadspeed + "," + fih.eyeXspeed + ","
							+ fih.eyeYspeed + "," + fih.jawspeed + ")\n";
				}
				if (tabs_main_checkbox_states[1]) {
					code12 = "i01.setArmSpeed(\"left\"," + fih.lbicepspeed
							+ "," + fih.lrotatespeed + "," + fih.lshoulderspeed
							+ "," + fih.lomoplatespeed + ")\n";
				}
				if (tabs_main_checkbox_states[2]) {
					code13 = "i01.setArmSpeed(\"right\"," + fih.rbicepspeed
							+ "," + fih.rrotatespeed + "," + fih.rshoulderspeed
							+ "," + fih.romoplatespeed + ")\n";
				}
				if (tabs_main_checkbox_states[3]) {
					code14 = "i01.setHandSpeed(\"left\"," + fih.lthumbspeed
							+ "," + fih.lindexspeed + "," + fih.lmajeurespeed
							+ "," + fih.lringfingerspeed + ","
							+ fih.lpinkyspeed + "," + fih.lwristspeed + ")\n";
				}
				if (tabs_main_checkbox_states[4]) {
					code15 = "i01.setHandSpeed(\"right\"," + fih.rthumbspeed
							+ "," + fih.rindexspeed + "," + fih.rmajeurespeed
							+ "," + fih.rringfingerspeed + ","
							+ fih.rpinkyspeed + "," + fih.rwristspeed + ")\n";
				}
				if (tabs_main_checkbox_states[5]) {
					code16 = "i01.setTorsoSpeed(" + fih.topStomspeed + ","
							+ fih.midStomspeed + "," + fih.lowStomspeed + ")\n";
				}
				code1 = code11 + code12 + code13 + code14 + code15 + code16;
			}
			code = code + code1;
		}
		generatedcode.setText(code);
	}

	public void frame_add(JList framelist, JTextField frame_add_textfield) {
		// Add a servo movement frame to the framelist (button bottom-right)
		FrameItemHolder fih = new FrameItemHolder();

		fih.rthumb = servoitemholder[0][0].sli.getValue();
		fih.rindex = servoitemholder[0][1].sli.getValue();
		fih.rmajeure = servoitemholder[0][2].sli.getValue();
		fih.rringfinger = servoitemholder[0][3].sli.getValue();
		fih.rpinky = servoitemholder[0][4].sli.getValue();
		fih.rwrist = servoitemholder[0][5].sli.getValue();

		fih.rbicep = servoitemholder[1][0].sli.getValue();
		fih.rrotate = servoitemholder[1][1].sli.getValue();
		fih.rshoulder = servoitemholder[1][2].sli.getValue();
		fih.romoplate = servoitemholder[1][3].sli.getValue();

		fih.lthumb = servoitemholder[2][0].sli.getValue();
		fih.lindex = servoitemholder[2][1].sli.getValue();
		fih.lmajeure = servoitemholder[2][2].sli.getValue();
		fih.lringfinger = servoitemholder[2][3].sli.getValue();
		fih.lpinky = servoitemholder[2][4].sli.getValue();
		fih.lwrist = servoitemholder[2][5].sli.getValue();

		fih.lbicep = servoitemholder[3][0].sli.getValue();
		fih.lrotate = servoitemholder[3][1].sli.getValue();
		fih.lshoulder = servoitemholder[3][2].sli.getValue();
		fih.lomoplate = servoitemholder[3][3].sli.getValue();

		fih.neck = servoitemholder[4][0].sli.getValue();
		fih.rothead = servoitemholder[4][1].sli.getValue();
		fih.eyeX = servoitemholder[4][2].sli.getValue();
		fih.eyeY = servoitemholder[4][3].sli.getValue();
		fih.jaw = servoitemholder[4][4].sli.getValue();

		fih.topStom = servoitemholder[5][0].sli.getValue();
		fih.midStom = servoitemholder[5][1].sli.getValue();
		fih.lowStom = servoitemholder[5][2].sli.getValue();

		fih.sleep = -1;
		fih.speech = null;
		fih.name = frame_add_textfield.getText();

		frameitemholder.add(fih);

		framelistact(framelist);
	}

	public void frame_addspeed(JList framelist) {
		// Add a speed setting frame to the framelist (button bottom-right)
		FrameItemHolder fih = new FrameItemHolder();

		fih.rthumbspeed = Float.parseFloat(servoitemholder[0][0].spe.getText());
		fih.rindexspeed = Float.parseFloat(servoitemholder[0][1].spe.getText());
		fih.rmajeurespeed = Float.parseFloat(servoitemholder[0][2].spe
				.getText());
		fih.rringfingerspeed = Float.parseFloat(servoitemholder[0][3].spe
				.getText());
		fih.rpinkyspeed = Float.parseFloat(servoitemholder[0][4].spe.getText());
		fih.rwristspeed = Float.parseFloat(servoitemholder[0][5].spe.getText());

		fih.rbicepspeed = Float.parseFloat(servoitemholder[1][0].spe.getText());
		fih.rrotatespeed = Float
				.parseFloat(servoitemholder[1][1].spe.getText());
		fih.rshoulderspeed = Float.parseFloat(servoitemholder[1][2].spe
				.getText());
		fih.romoplatespeed = Float.parseFloat(servoitemholder[1][3].spe
				.getText());

		fih.lthumbspeed = Float.parseFloat(servoitemholder[2][0].spe.getText());
		fih.lindexspeed = Float.parseFloat(servoitemholder[2][1].spe.getText());
		fih.lmajeurespeed = Float.parseFloat(servoitemholder[2][2].spe
				.getText());
		fih.lringfingerspeed = Float.parseFloat(servoitemholder[2][3].spe
				.getText());
		fih.lpinkyspeed = Float.parseFloat(servoitemholder[2][4].spe.getText());
		fih.lwristspeed = Float.parseFloat(servoitemholder[2][5].spe.getText());

		fih.lbicepspeed = Float.parseFloat(servoitemholder[3][0].spe.getText());
		fih.lrotatespeed = Float
				.parseFloat(servoitemholder[3][1].spe.getText());
		fih.lshoulderspeed = Float.parseFloat(servoitemholder[3][2].spe
				.getText());
		fih.lomoplatespeed = Float.parseFloat(servoitemholder[3][3].spe
				.getText());

		fih.neckspeed = Float.parseFloat(servoitemholder[4][0].spe.getText());
		fih.rotheadspeed = Float
				.parseFloat(servoitemholder[4][1].spe.getText());
		fih.eyeXspeed = Float.parseFloat(servoitemholder[4][2].spe.getText());
		fih.eyeYspeed = Float.parseFloat(servoitemholder[4][3].spe.getText());
		fih.jawspeed = Float.parseFloat(servoitemholder[4][4].spe.getText());

		fih.topStomspeed = Float
				.parseFloat(servoitemholder[5][0].spe.getText());
		fih.midStomspeed = Float
				.parseFloat(servoitemholder[5][1].spe.getText());
		fih.lowStomspeed = Float
				.parseFloat(servoitemholder[5][2].spe.getText());

		fih.sleep = -1;
		fih.speech = null;
		fih.name = null;

		frameitemholder.add(fih);

		framelistact(framelist);
	}

	public void frame_addsleep(JList framelist,
			JTextField frame_addsleep_textfield) {
		// Add a sleep frame to the framelist (button bottom-right)
		FrameItemHolder fih = new FrameItemHolder();

		fih.sleep = Integer.parseInt(frame_addsleep_textfield.getText());
		fih.speech = null;
		fih.name = null;

		frameitemholder.add(fih);

		framelistact(framelist);
	}

	public void frame_addspeech(JList framelist,
			JTextField frame_addspeech_textfield) {
		// Add a speech frame to the framelist (button bottom-right)
		FrameItemHolder fih = new FrameItemHolder();

		fih.sleep = -1;
		fih.speech = frame_addspeech_textfield.getText();
		fih.name = null;

		frameitemholder.add(fih);

		framelistact(framelist);
	}

	public void frame_importminresmax() {
		// Import the Min- / Res- / Max- settings of your InMoov
		for (int i1 = 0; i1 < servoitemholder.length; i1++) {
			for (int i2 = 0; i2 < servoitemholder[i1].length; i2++) {
				InMoovHand inmhand = null;
				InMoovArm inmarm = null;
				InMoovHead inmhead = null;
				InMoovTorso inmtorso = null;

				if (i1 == 0) {
					inmhand = i01.rightHand;
				} else if (i1 == 1) {
					inmarm = i01.rightArm;
				} else if (i1 == 2) {
					inmhand = i01.leftHand;
				} else if (i1 == 3) {
					inmarm = i01.rightArm;
				} else if (i1 == 4) {
					inmhead = i01.head;
				} else if (i1 == 5) {
					inmtorso = i01.torso;
				}

				Servo servo = null;

				if (i1 == 0 || i1 == 2) {
					if (i2 == 0) {
						servo = inmhand.thumb;
					} else if (i2 == 1) {
						servo = inmhand.index;
					} else if (i2 == 2) {
						servo = inmhand.majeure;
					} else if (i2 == 3) {
						servo = inmhand.ringFinger;
					} else if (i2 == 4) {
						servo = inmhand.pinky;
					} else if (i2 == 5) {
						servo = inmhand.wrist;
					}
				} else if (i1 == 1 || i1 == 3) {
					if (i2 == 0) {
						servo = inmarm.bicep;
					} else if (i2 == 1) {
						servo = inmarm.rotate;
					} else if (i2 == 2) {
						servo = inmarm.shoulder;
					} else if (i2 == 3) {
						servo = inmarm.omoplate;
					}
				} else if (i1 == 4) {
					if (i2 == 0) {
						servo = inmhead.neck;
					} else if (i2 == 1) {
						servo = inmhead.rothead;
					} else if (i2 == 2) {
						servo = inmhead.eyeX;
					} else if (i2 == 3) {
						servo = inmhead.eyeY;
					} else if (i2 == 4) {
						servo = inmhead.jaw;
					}
				} else if (i1 == 5) {
					if (i2 == 0) {
						servo = inmtorso.topStom;
					} else if (i2 == 1) {
						servo = inmtorso.midStom;
					} else if (i2 == 2) {
						servo = inmtorso.lowStom;
					}
				}

				int min = servo.getMin();
				int res = servo.getRest();
				int max = servo.getMax();

				servoitemholder[i1][i2].min.setText(min + "");
				servoitemholder[i1][i2].res.setText(res + "");
				servoitemholder[i1][i2].max.setText(max + "");
				servoitemholder[i1][i2].sli.setMinimum(min);
				servoitemholder[i1][i2].sli.setMaximum(max);
				servoitemholder[i1][i2].sli.setValue(res);
			}
		}
	}

	public void frame_remove(JList framelist) {
		// Remove this frame from the framelist (button bottom-right)
		int pos = framelist.getSelectedIndex();
		if (pos != -1) {
			frameitemholder.remove(pos);

			framelistact(framelist);
		}
	}

	public void frame_load(JList framelist, JTextField frame_add_textfield,
			JTextField frame_addsleep_textfield,
			JTextField frame_addspeech_textfield) {
		// Load this frame from the framelist (button bottom-right)
		int pos = framelist.getSelectedIndex();

		if (pos != -1) {

			// sleep || speech || servo movement || speed setting
			if (frameitemholder.get(pos).sleep != -1) {
				frame_addsleep_textfield.setText(frameitemholder.get(pos).sleep
						+ "");
			} else if (frameitemholder.get(pos).speech != null) {
				frame_addspeech_textfield
						.setText(frameitemholder.get(pos).speech);
			} else if (frameitemholder.get(pos).name != null) {
				servoitemholder[0][0].sli
						.setValue(frameitemholder.get(pos).rthumb);
				servoitemholder[0][1].sli
						.setValue(frameitemholder.get(pos).rindex);
				servoitemholder[0][2].sli
						.setValue(frameitemholder.get(pos).rmajeure);
				servoitemholder[0][3].sli
						.setValue(frameitemholder.get(pos).rringfinger);
				servoitemholder[0][4].sli
						.setValue(frameitemholder.get(pos).rpinky);
				servoitemholder[0][5].sli
						.setValue(frameitemholder.get(pos).rwrist);

				servoitemholder[1][0].sli
						.setValue(frameitemholder.get(pos).rbicep);
				servoitemholder[1][1].sli
						.setValue(frameitemholder.get(pos).rrotate);
				servoitemholder[1][2].sli
						.setValue(frameitemholder.get(pos).rshoulder);
				servoitemholder[1][3].sli
						.setValue(frameitemholder.get(pos).romoplate);

				servoitemholder[2][0].sli
						.setValue(frameitemholder.get(pos).lthumb);
				servoitemholder[2][1].sli
						.setValue(frameitemholder.get(pos).lindex);
				servoitemholder[2][2].sli
						.setValue(frameitemholder.get(pos).lmajeure);
				servoitemholder[2][3].sli
						.setValue(frameitemholder.get(pos).lringfinger);
				servoitemholder[2][4].sli
						.setValue(frameitemholder.get(pos).lpinky);
				servoitemholder[2][5].sli
						.setValue(frameitemholder.get(pos).lwrist);

				servoitemholder[3][0].sli
						.setValue(frameitemholder.get(pos).lbicep);
				servoitemholder[3][1].sli
						.setValue(frameitemholder.get(pos).lrotate);
				servoitemholder[3][2].sli
						.setValue(frameitemholder.get(pos).lshoulder);
				servoitemholder[3][3].sli
						.setValue(frameitemholder.get(pos).lomoplate);

				servoitemholder[4][0].sli
						.setValue(frameitemholder.get(pos).neck);
				servoitemholder[4][1].sli
						.setValue(frameitemholder.get(pos).rothead);
				servoitemholder[4][2].sli
						.setValue(frameitemholder.get(pos).eyeX);
				servoitemholder[4][3].sli
						.setValue(frameitemholder.get(pos).eyeY);
				servoitemholder[4][4].sli
						.setValue(frameitemholder.get(pos).jaw);

				servoitemholder[5][0].sli
						.setValue(frameitemholder.get(pos).topStom);
				servoitemholder[5][1].sli
						.setValue(frameitemholder.get(pos).midStom);
				servoitemholder[5][2].sli
						.setValue(frameitemholder.get(pos).lowStom);
				frame_add_textfield.setText(frameitemholder.get(pos).name);
			} else {
				servoitemholder[0][0].spe
						.setText(frameitemholder.get(pos).rthumbspeed + "");
				servoitemholder[0][1].spe
						.setText(frameitemholder.get(pos).rindexspeed + "");
				servoitemholder[0][2].spe
						.setText(frameitemholder.get(pos).rmajeurespeed + "");
				servoitemholder[0][3].spe
						.setText(frameitemholder.get(pos).rringfingerspeed + "");
				servoitemholder[0][4].spe
						.setText(frameitemholder.get(pos).rpinkyspeed + "");
				servoitemholder[0][5].spe
						.setText(frameitemholder.get(pos).rwristspeed + "");

				servoitemholder[1][0].spe
						.setText(frameitemholder.get(pos).rbicepspeed + "");
				servoitemholder[1][1].spe
						.setText(frameitemholder.get(pos).rrotatespeed + "");
				servoitemholder[1][2].spe
						.setText(frameitemholder.get(pos).rshoulderspeed + "");
				servoitemholder[1][3].spe
						.setText(frameitemholder.get(pos).romoplatespeed + "");

				servoitemholder[2][0].spe
						.setText(frameitemholder.get(pos).lthumbspeed + "");
				servoitemholder[2][1].spe
						.setText(frameitemholder.get(pos).lindexspeed + "");
				servoitemholder[2][2].spe
						.setText(frameitemholder.get(pos).lmajeurespeed + "");
				servoitemholder[2][3].spe
						.setText(frameitemholder.get(pos).lringfingerspeed + "");
				servoitemholder[2][4].spe
						.setText(frameitemholder.get(pos).lpinkyspeed + "");
				servoitemholder[2][5].spe
						.setText(frameitemholder.get(pos).lwristspeed + "");

				servoitemholder[3][0].spe
						.setText(frameitemholder.get(pos).lbicepspeed + "");
				servoitemholder[3][1].spe
						.setText(frameitemholder.get(pos).lrotatespeed + "");
				servoitemholder[3][2].spe
						.setText(frameitemholder.get(pos).lshoulderspeed + "");
				servoitemholder[3][3].spe
						.setText(frameitemholder.get(pos).lomoplatespeed + "");

				servoitemholder[4][0].spe
						.setText(frameitemholder.get(pos).neckspeed + "");
				servoitemholder[4][1].spe
						.setText(frameitemholder.get(pos).rotheadspeed + "");
				servoitemholder[4][2].spe
						.setText(frameitemholder.get(pos).eyeXspeed + "");
				servoitemholder[4][3].spe
						.setText(frameitemholder.get(pos).eyeYspeed + "");
				servoitemholder[4][4].spe
						.setText(frameitemholder.get(pos).jawspeed + "");

				servoitemholder[5][0].spe
						.setText(frameitemholder.get(pos).topStomspeed + "");
				servoitemholder[5][1].spe
						.setText(frameitemholder.get(pos).midStomspeed + "");
				servoitemholder[5][2].spe
						.setText(frameitemholder.get(pos).lowStomspeed + "");
			}
		}
	}

	public void frame_update(JList framelist, JTextField frame_add_textfield,
			JTextField frame_addsleep_textfield,
			JTextField frame_addspeech_textfield) {
		// Update this frame on the framelist (button bottom-right)

		int pos = framelist.getSelectedIndex();

		if (pos != -1) {
			FrameItemHolder fih = new FrameItemHolder();

			// sleep || speech || servo movement || speed setting
			if (frameitemholder.get(pos).sleep != -1) {
				fih.sleep = Integer
						.parseInt(frame_addsleep_textfield.getText());
				fih.speech = null;
				fih.name = null;
			} else if (frameitemholder.get(pos).speech != null) {
				fih.sleep = -1;
				fih.speech = frame_addspeech_textfield.getText();
				fih.name = null;
			} else if (frameitemholder.get(pos).name != null) {
				fih.rthumb = servoitemholder[0][0].sli.getValue();
				fih.rindex = servoitemholder[0][1].sli.getValue();
				fih.rmajeure = servoitemholder[0][2].sli.getValue();
				fih.rringfinger = servoitemholder[0][3].sli.getValue();
				fih.rpinky = servoitemholder[0][4].sli.getValue();
				fih.rwrist = servoitemholder[0][5].sli.getValue();

				fih.rbicep = servoitemholder[1][0].sli.getValue();
				fih.rrotate = servoitemholder[1][1].sli.getValue();
				fih.rshoulder = servoitemholder[1][2].sli.getValue();
				fih.romoplate = servoitemholder[1][3].sli.getValue();

				fih.lthumb = servoitemholder[2][0].sli.getValue();
				fih.lindex = servoitemholder[2][1].sli.getValue();
				fih.lmajeure = servoitemholder[2][2].sli.getValue();
				fih.lringfinger = servoitemholder[2][3].sli.getValue();
				fih.lpinky = servoitemholder[2][4].sli.getValue();
				fih.lwrist = servoitemholder[2][5].sli.getValue();

				fih.lbicep = servoitemholder[3][0].sli.getValue();
				fih.lrotate = servoitemholder[3][1].sli.getValue();
				fih.lshoulder = servoitemholder[3][2].sli.getValue();
				fih.lomoplate = servoitemholder[3][3].sli.getValue();

				fih.neck = servoitemholder[4][0].sli.getValue();
				fih.rothead = servoitemholder[4][1].sli.getValue();
				fih.eyeX = servoitemholder[4][2].sli.getValue();
				fih.eyeY = servoitemholder[4][3].sli.getValue();
				fih.jaw = servoitemholder[4][4].sli.getValue();

				fih.topStom = servoitemholder[5][0].sli.getValue();
				fih.midStom = servoitemholder[5][1].sli.getValue();
				fih.lowStom = servoitemholder[5][2].sli.getValue();

				fih.sleep = -1;
				fih.speech = null;
				fih.name = frame_add_textfield.getText();
			} else {
				fih.rthumbspeed = Float.parseFloat(servoitemholder[0][0].spe
						.getText());
				fih.rindexspeed = Float.parseFloat(servoitemholder[0][1].spe
						.getText());
				fih.rmajeurespeed = Float.parseFloat(servoitemholder[0][2].spe
						.getText());
				fih.rringfingerspeed = Float
						.parseFloat(servoitemholder[0][3].spe.getText());
				fih.rpinkyspeed = Float.parseFloat(servoitemholder[0][4].spe
						.getText());
				fih.rwristspeed = Float.parseFloat(servoitemholder[0][5].spe
						.getText());

				fih.rbicepspeed = Float.parseFloat(servoitemholder[1][0].spe
						.getText());
				fih.rrotatespeed = Float.parseFloat(servoitemholder[1][1].spe
						.getText());
				fih.rshoulderspeed = Float.parseFloat(servoitemholder[1][2].spe
						.getText());
				fih.romoplatespeed = Float.parseFloat(servoitemholder[1][3].spe
						.getText());

				fih.lthumbspeed = Float.parseFloat(servoitemholder[2][0].spe
						.getText());
				fih.lindexspeed = Float.parseFloat(servoitemholder[2][1].spe
						.getText());
				fih.lmajeurespeed = Float.parseFloat(servoitemholder[2][2].spe
						.getText());
				fih.lringfingerspeed = Float
						.parseFloat(servoitemholder[2][3].spe.getText());
				fih.lpinkyspeed = Float.parseFloat(servoitemholder[2][4].spe
						.getText());
				fih.lwristspeed = Float.parseFloat(servoitemholder[2][5].spe
						.getText());

				fih.lbicepspeed = Float.parseFloat(servoitemholder[3][0].spe
						.getText());
				fih.lrotatespeed = Float.parseFloat(servoitemholder[3][1].spe
						.getText());
				fih.lshoulderspeed = Float.parseFloat(servoitemholder[3][2].spe
						.getText());
				fih.lomoplatespeed = Float.parseFloat(servoitemholder[3][3].spe
						.getText());

				fih.neckspeed = Float.parseFloat(servoitemholder[4][0].spe
						.getText());
				fih.rotheadspeed = Float.parseFloat(servoitemholder[4][1].spe
						.getText());
				fih.eyeXspeed = Float.parseFloat(servoitemholder[4][2].spe
						.getText());
				fih.eyeYspeed = Float.parseFloat(servoitemholder[4][3].spe
						.getText());
				fih.jawspeed = Float.parseFloat(servoitemholder[4][4].spe
						.getText());

				fih.topStomspeed = Float.parseFloat(servoitemholder[5][0].spe
						.getText());
				fih.midStomspeed = Float.parseFloat(servoitemholder[5][1].spe
						.getText());
				fih.lowStomspeed = Float.parseFloat(servoitemholder[5][2].spe
						.getText());

				fih.sleep = -1;
				fih.speech = null;
				fih.name = null;
			}
			frameitemholder.set(pos, fih);

			framelistact(framelist);
		}
	}

	public void frame_copy(JList framelist) {
		// Copy this frame on the framelist (button bottom-right)
		int pos = framelist.getSelectedIndex();

		if (pos != -1) {
			FrameItemHolder fih = frameitemholder.get(pos);
			frameitemholder.add(fih);

			framelistact(framelist);
		}
	}

	public void frame_up(JList framelist) {
		// Move this frame one up on the framelist (button bottom-right)
		int pos = framelist.getSelectedIndex();

		if (pos != -1) {
			FrameItemHolder fih = frameitemholder.remove(pos);
			frameitemholder.add(pos - 1, fih);

			framelistact(framelist);
		}
	}

	public void frame_down(JList framelist) {
		// Move this frame one down on the framelist (button bottom-right)
		int pos = framelist.getSelectedIndex();

		if (pos != -1) {
			FrameItemHolder fih = frameitemholder.remove(pos);
			frameitemholder.add(pos + 1, fih);

			framelistact(framelist);
		}
	}

	public void frame_test(JList framelist) {
		// Test this frame (execute)
		int pos = framelist.getSelectedIndex();
		if (i01 != null && pos != -1) {
			FrameItemHolder fih = frameitemholder.get(pos);

			// sleep || speech || servo movement || speed setting
			if (fih.sleep != -1) {
				sleep(fih.sleep);
			} else if (fih.speech != null) {
				i01.mouth.speakBlocking(fih.speech);
			} else if (fih.name != null) {
				if (tabs_main_checkbox_states[0]) {
					i01.moveHead(fih.neck, fih.rothead, fih.eyeX, fih.eyeY,
							fih.jaw);
				}
				if (tabs_main_checkbox_states[1]) {
					i01.moveArm("left", fih.lbicep, fih.lrotate, fih.lshoulder,
							fih.lomoplate);
				}
				if (tabs_main_checkbox_states[2]) {
					i01.moveArm("right", fih.rbicep, fih.rrotate,
							fih.rshoulder, fih.romoplate);
				}
				if (tabs_main_checkbox_states[3]) {
					i01.moveHand("left", fih.lthumb, fih.lindex, fih.lmajeure,
							fih.lringfinger, fih.lpinky, fih.lwrist);
				}
				if (tabs_main_checkbox_states[4]) {
					i01.moveHand("right", fih.rthumb, fih.rindex, fih.rmajeure,
							fih.rringfinger, fih.rpinky, fih.rwrist);
				}
				if (tabs_main_checkbox_states[5]) {
					i01.moveTorso(fih.topStom, fih.midStom, fih.lowStom);
				}
			} else {
				if (tabs_main_checkbox_states[0]) {
					i01.setHeadSpeed(fih.neckspeed, fih.rotheadspeed,
							fih.eyeXspeed, fih.eyeYspeed, fih.jawspeed);
				}
				if (tabs_main_checkbox_states[1]) {
					i01.setArmSpeed("left", fih.lbicepspeed, fih.lrotatespeed,
							fih.lshoulderspeed, fih.lomoplatespeed);
				}
				if (tabs_main_checkbox_states[2]) {
					i01.setArmSpeed("right", fih.rbicepspeed, fih.rrotatespeed,
							fih.rshoulderspeed, fih.romoplatespeed);
				}
				if (tabs_main_checkbox_states[3]) {
					i01.setHandSpeed("left", fih.lthumbspeed, fih.lindexspeed,
							fih.lmajeurespeed, fih.lringfingerspeed,
							fih.lpinkyspeed, fih.lwristspeed);
				}
				if (tabs_main_checkbox_states[4]) {
					i01.setHandSpeed("right", fih.rthumbspeed, fih.rindexspeed,
							fih.rmajeurespeed, fih.rringfingerspeed,
							fih.rpinkyspeed, fih.rwristspeed);
				}
				if (tabs_main_checkbox_states[5]) {
					i01.setTorsoSpeed(fih.topStomspeed, fih.midStomspeed,
							fih.lowStomspeed);
				}
			}
		}
	}

	public void framelistact(JList framelist) {
		// Re-Build the framelist
		String[] listdata = new String[frameitemholder.size()];

		for (int i = 0; i < frameitemholder.size(); i++) {
			FrameItemHolder fih = frameitemholder.get(i);

			String displaytext = "";

			// servo movement || sleep || speech || speed setting
			if (fih.sleep != -1) {
				displaytext = "SLEEP   " + fih.sleep;
			} else if (fih.speech != null) {
				displaytext = "SPEECH   " + fih.speech;
			} else if (fih.name != null) {
				String displaytext1 = "";
				String displaytext2 = "";
				String displaytext3 = "";
				String displaytext4 = "";
				String displaytext5 = "";
				String displaytext6 = "";
				if (tabs_main_checkbox_states[0]) {
					displaytext1 = fih.rthumb + " " + fih.rindex + " "
							+ fih.rmajeure + " " + fih.rringfinger + " "
							+ fih.rpinky + " " + fih.rwrist;
				}
				if (tabs_main_checkbox_states[1]) {
					displaytext2 = fih.rbicep + " " + fih.rrotate + " "
							+ fih.rshoulder + " " + fih.romoplate;
				}
				if (tabs_main_checkbox_states[2]) {
					displaytext3 = fih.lthumb + " " + fih.lindex + " "
							+ fih.lmajeure + " " + fih.lringfinger + " "
							+ fih.lpinky + " " + fih.lwrist;
				}
				if (tabs_main_checkbox_states[3]) {
					displaytext4 = fih.lbicep + " " + fih.lrotate + " "
							+ fih.lshoulder + " " + fih.lomoplate;
				}
				if (tabs_main_checkbox_states[4]) {
					displaytext5 = fih.neck + " " + fih.rothead + " "
							+ fih.eyeX + " " + fih.eyeY + " " + fih.jaw;
				}
				if (tabs_main_checkbox_states[5]) {
					displaytext6 = fih.topStom + " " + fih.midStom + " "
							+ fih.lowStom;
				}
				displaytext = fih.name + ": " + displaytext1 + " | "
						+ displaytext2 + " | " + displaytext3 + " | "
						+ displaytext4 + " | " + displaytext5 + " | "
						+ displaytext6;
			} else {
				String displaytext1 = "";
				String displaytext2 = "";
				String displaytext3 = "";
				String displaytext4 = "";
				String displaytext5 = "";
				String displaytext6 = "";
				if (tabs_main_checkbox_states[0]) {
					displaytext1 = fih.rthumbspeed + " " + fih.rindexspeed
							+ " " + fih.rmajeurespeed + " "
							+ fih.rringfingerspeed + " " + fih.rpinkyspeed
							+ " " + fih.rwristspeed;
				}
				if (tabs_main_checkbox_states[1]) {
					displaytext2 = fih.rbicepspeed + " " + fih.rrotatespeed
							+ " " + fih.rshoulderspeed + " "
							+ fih.romoplatespeed;
				}
				if (tabs_main_checkbox_states[2]) {
					displaytext3 = fih.lthumbspeed + " " + fih.lindexspeed
							+ " " + fih.lmajeurespeed + " "
							+ fih.lringfingerspeed + " " + fih.lpinkyspeed
							+ " " + fih.lwristspeed;
				}
				if (tabs_main_checkbox_states[3]) {
					displaytext4 = fih.lbicepspeed + " " + fih.lrotatespeed
							+ " " + fih.lshoulderspeed + " "
							+ fih.lomoplatespeed;
				}
				if (tabs_main_checkbox_states[4]) {
					displaytext5 = fih.neckspeed + " " + fih.rotheadspeed + " "
							+ fih.eyeXspeed + " " + fih.eyeYspeed + " "
							+ fih.jawspeed;
				}
				if (tabs_main_checkbox_states[5]) {
					displaytext6 = fih.topStomspeed + " " + fih.midStomspeed
							+ " " + fih.lowStomspeed;
				}
				displaytext = "SPEED   " + displaytext1 + " | " + displaytext2
						+ " | " + displaytext3 + " | " + displaytext4 + " | "
						+ displaytext5 + " | " + displaytext6;
			}
			listdata[i] = displaytext;
		}

		framelist.setListData(listdata);
	}

	public void frame_moverealtime(JCheckBox frame_moverealtime) {
		moverealtime = frame_moverealtime.isSelected();
	}

	public void servoitemholder_slider_changed(int t1, int t2) {
		// One slider were adjusted
		servoitemholder[t1][t2].akt.setText(servoitemholder[t1][t2].sli
				.getValue() + "");
		// Move the Servos in "Real-Time"
		if (moverealtime && i01 != null) {
			FrameItemHolder fih = new FrameItemHolder();

			fih.rthumb = servoitemholder[0][0].sli.getValue();
			fih.rindex = servoitemholder[0][1].sli.getValue();
			fih.rmajeure = servoitemholder[0][2].sli.getValue();
			fih.rringfinger = servoitemholder[0][3].sli.getValue();
			fih.rpinky = servoitemholder[0][4].sli.getValue();
			fih.rwrist = servoitemholder[0][5].sli.getValue();

			fih.rbicep = servoitemholder[1][0].sli.getValue();
			fih.rrotate = servoitemholder[1][1].sli.getValue();
			fih.rshoulder = servoitemholder[1][2].sli.getValue();
			fih.romoplate = servoitemholder[1][3].sli.getValue();

			fih.lthumb = servoitemholder[2][0].sli.getValue();
			fih.lindex = servoitemholder[2][1].sli.getValue();
			fih.lmajeure = servoitemholder[2][2].sli.getValue();
			fih.lringfinger = servoitemholder[2][3].sli.getValue();
			fih.lpinky = servoitemholder[2][4].sli.getValue();
			fih.lwrist = servoitemholder[2][5].sli.getValue();

			fih.lbicep = servoitemholder[3][0].sli.getValue();
			fih.lrotate = servoitemholder[3][1].sli.getValue();
			fih.lshoulder = servoitemholder[3][2].sli.getValue();
			fih.lomoplate = servoitemholder[3][3].sli.getValue();

			fih.neck = servoitemholder[4][0].sli.getValue();
			fih.rothead = servoitemholder[4][1].sli.getValue();
			fih.eyeX = servoitemholder[4][2].sli.getValue();
			fih.eyeY = servoitemholder[4][3].sli.getValue();
			fih.jaw = servoitemholder[4][4].sli.getValue();

			fih.topStom = servoitemholder[5][0].sli.getValue();
			fih.midStom = servoitemholder[5][1].sli.getValue();
			fih.lowStom = servoitemholder[5][2].sli.getValue();

			if (tabs_main_checkbox_states[0]) {
				i01.moveHead(fih.neck, fih.rothead, fih.eyeX, fih.eyeY, fih.jaw);
			}
			if (tabs_main_checkbox_states[1]) {
				i01.moveArm("left", fih.lbicep, fih.lrotate, fih.lshoulder,
						fih.lomoplate);
			}
			if (tabs_main_checkbox_states[2]) {
				i01.moveArm("right", fih.rbicep, fih.rrotate, fih.rshoulder,
						fih.romoplate);
			}
			if (tabs_main_checkbox_states[3]) {
				i01.moveHand("left", fih.lthumb, fih.lindex, fih.lmajeure,
						fih.lringfinger, fih.lpinky, fih.lwrist);
			}
			if (tabs_main_checkbox_states[4]) {
				i01.moveHand("right", fih.rthumb, fih.rindex, fih.rmajeure,
						fih.rringfinger, fih.rpinky, fih.rwrist);
			}
			if (tabs_main_checkbox_states[5]) {
				i01.moveTorso(fih.topStom, fih.midStom, fih.lowStom);
			}
		}
	}

	public void servoitemholder_set_sih1(int i1, ServoItemHolder[] sih1) {
		// Setting references
		servoitemholder[i1] = sih1;
	}

	public static class ServoItemHolder {

		public JLabel fin;
		public JLabel min;
		public JLabel res;
		public JLabel max;
		public JSlider sli;
		public JLabel akt;
		public JTextField spe;
	}

	public static class FrameItemHolder {

		int rthumb, rindex, rmajeure, rringfinger, rpinky, rwrist;
		int rbicep, rrotate, rshoulder, romoplate;
		int lthumb, lindex, lmajeure, lringfinger, lpinky, lwrist;
		int lbicep, lrotate, lshoulder, lomoplate;
		int neck, rothead, eyeX, eyeY, jaw;
		int topStom, midStom, lowStom;
		float rthumbspeed, rindexspeed, rmajeurespeed, rringfingerspeed,
				rpinkyspeed, rwristspeed;
		float rbicepspeed, rrotatespeed, rshoulderspeed, romoplatespeed;
		float lthumbspeed, lindexspeed, lmajeurespeed, lringfingerspeed,
				lpinkyspeed, lwristspeed;
		float lbicepspeed, lrotatespeed, lshoulderspeed, lomoplatespeed;
		float neckspeed, rotheadspeed, eyeXspeed, eyeYspeed, jawspeed;
		float topStomspeed, midStomspeed, lowStomspeed;
		int speed;
		int sleep;
		String speech;
		String name;
	}

	public static void main(String[] args) throws InterruptedException {

		LoggingFactory.getInstance().configure();
		LoggingFactory.getInstance().setLevel(Level.INFO);
		try {

			Runtime.start("gui", "GUIService");
			Runtime.start("inmoovgesturecreator", "InMoovGestureCreator");

		} catch (Exception e) {
			Logging.logException(e);
		}

	}

}
