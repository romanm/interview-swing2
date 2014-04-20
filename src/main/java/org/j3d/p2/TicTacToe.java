package org.j3d.p2;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PointLight;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;

@SuppressWarnings("serial")
/**
 * TicTacToe3D.java
 * Plays a game of Tic-Tac-Toe... in 3D!
 * http://java.sun.com/developer/onlineTraining/java3d/j3d_tutorial_ch1.pdf
 * @author Jamie Langille
 * @version Date: 12/06/2011
 */
public class TicTacToe extends JFrame implements MouseListener {

	// Main method/procedure: runs program
	public static void main(String[] args) {
		TicTacToe model = new TicTacToe();
		model.setDefaultCloseOperation(EXIT_ON_CLOSE);
		model.setVisible(true);
	}

	// Data used by the program

	// 3D/GUI stuff
	private JPanel panel;
	private BranchGroup scene;
	private PickCanvas pCanvas;
	private BranchGroup objRoot;
	private TransformGroup tg, objSpin;
	private final String[] textures = new String[]{"alien.jpg", "eye.jpg",
			"lava.jpg", "robot.jpg", "tron.jpg", "earth.jpg"};

	// Game play/UI data
	private boolean turn = true, music = false;
	private int grid[][][] = new int[3][3][3];
	private Sphere sGrid[][][] = new Sphere[3][3][3];
	private Color c1, c2;
	private AudioClip ac;

	public TicTacToe() {

		// Music (if applicable)
		URL url = TicTacToe.class.getResource("descent" +
				".wav");
		if (url != null){
			ac = Applet.newAudioClip(url);
			//			ac.loop();
		}
		else
			System.err.println("Music not loaded.");

		// Setup window
		this.setLocation(1920 / 2 - 250, 1080 / 2 - 250);
		this.setSize(500, 500);
		this.setTitle("Tic Tac Toe     By: Jamie Langille");

		// Get data from the user (colours and instructions to play)
		JOptionPane.showMessageDialog(this, "Get 2 Rows To Win!", "How to Play", JOptionPane.INFORMATION_MESSAGE);
		while (c1 == null)
			c1 = JColorChooser.showDialog(this, "Choose A Color, Player 1", Color.RED);
		while (c2 == null)
			c2 = JColorChooser.showDialog(this, "Choose A Color, Player 2", Color.BLUE);

		// Set-up 3D environment

		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		objRoot = new BranchGroup();
		pCanvas = new PickCanvas(canvas3D, objRoot);
		pCanvas.setMode(PickCanvas.GEOMETRY);
		canvas3D.addMouseListener(this);

		panel.add(canvas3D, BorderLayout.CENTER);

		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

		scene = createSceneGraph();
		scene.compile();

		simpleU.getViewingPlatform().setNominalViewingTransform();

		simpleU.addBranchGraph(scene);

		this.add(panel);
	}

	/**
	 * Creates the 3-Dimensional "Scene"
	 * 
	 * @return the scene, compiled, in the form of a <code>BranchGroup</code>
	 *         object.
	 */
	public BranchGroup createSceneGraph() {

		// Setup complicated stuff
		tg = new TransformGroup();

		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		objRoot.addChild(tg);
		tg.addChild(objSpin);

		// Add Spheres (White initially)
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					// addSphere() both adds a sphere to objRotate, and returns
					// that object for reference
					sGrid[x + 1][y + 1][z + 1] = addSphere(x, y, z, Color.GRAY, 0.075f, 2);
				}
			}
		}

		// Lighting (Ambient/Background)
		AmbientLight aLight = new AmbientLight();
		aLight.setCapability(PointLight.ALLOW_POSITION_WRITE);
		aLight.setColor(new Color3f(Color.WHITE));
		aLight.setInfluencingBounds(new BoundingSphere());
		objRoot.addChild(aLight);

		// Lighting (Point Lights)
		for (int i = 0; i < 5; i++) {
			PointLight pLight = new PointLight();
			pLight.setCapability(PointLight.ALLOW_POSITION_WRITE);
			pLight.setColor(new Color3f(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256))));
			pLight.setPosition(new Point3f((float) (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1)));
			pLight.setInfluencingBounds(new BoundingSphere());
			objRoot.addChild(pLight);
		}

		// Mouse Rotation
		MouseRotate mouse = new MouseRotate(tg);
		mouse.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(mouse);

		// Animation
		// RotationInterpolator rot = new RotationInterpolator(new Alpha(-1,
		// 5000), objSpin);
		// rot.setSchedulingBounds(new BoundingSphere());
		// objRoot.addChild(rot);

		// Compile/show scene
		objRoot.compile();
		return objRoot;
	}

	/**
	 * Adds a sphere to 'objRotate' (objects that can rotate)
	 * 
	 * @param x
	 *            the array-x coordinate
	 * @param y
	 *            the array-x coordinate
	 * @param z
	 *            the array-x coordinate
	 * @param c
	 *            the array-x coordinate
	 * @param dim
	 *            the radius of the sphere
	 * @param tex
	 *            the array-index of the texture image in 'textures'
	 * @return Sphere - the sphere that was created and added
	 */
	public Sphere addSphere(int x, int y, int z, Color c, float dim, int tex) {
		int primflags = Primitive.GENERATE_NORMALS
				+ Primitive.GENERATE_TEXTURE_COORDS;

		Sphere s = new Sphere(dim, primflags, 100);
		s.setCapability(Node.ENABLE_PICK_REPORTING);
		Appearance sApp = new Appearance();
		sApp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);

		// Texturing
		TextureLoader loader = new TextureLoader(textures[tex], "LUMINANCE", new Container());
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 0.0f, 0.0f, 0.0f));

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setCapability(TextureAttributes.ALLOW_MODE_READ);
		texAttr.setCapability(TextureAttributes.ALLOW_MODE_WRITE);
		texAttr.setTextureMode(TextureAttributes.COMBINE);
		sApp.setTexture(texture);
		sApp.setTextureAttributes(texAttr);

		Material sMat = new Material();
		sMat.setCapability(Material.ALLOW_COMPONENT_READ);
		sMat.setCapability(Material.ALLOW_COMPONENT_WRITE);
		sMat.setAmbientColor(new Color3f(c));
		sApp.setMaterial(sMat);

		s.setAppearance(sApp);

		TransformGroup tg2 = new TransformGroup();
		tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg2.addChild(s);
		Transform3D trans = new Transform3D();
		trans.setTranslation(new Vector3d(x * 0.3f, y * 0.3f, z * 0.3f));
		tg2.setTransform(trans);

		objSpin.addChild(tg2);
		return s;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	// Mouse Input (Clicking) - this works better than 'mouseClicked'
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON2 && ac != null) {
			if (music)
				ac.stop();
			else
				ac.loop();
			music = !music;
			return;
		}

		pCanvas.setShapeLocation(e);
		PickResult result = pCanvas.pickClosest();
		if (result != null) {
			Object obj = result.getNode(PickResult.PRIMITIVE);
			if (obj instanceof Sphere) {
				Sphere s = (Sphere) obj;
				if (e.getButton() == MouseEvent.BUTTON1) {
					int x = -2, y = -2, z = -2;
					LOOP : for (x = -1; x <= 1; x++) {
						for (y = -1; y <= 1; y++) {
							for (z = -1; z <= 1; z++) {
								if (sGrid[x + 1][y + 1][z + 1].equals(s))
									break LOOP;
							}
						}
					}
					if (grid[x + 1][y + 1][z + 1] != 0)
						return;
					grid[x + 1][y + 1][z + 1] = turn ? 1 : -1;
					s.getAppearance().getMaterial().setAmbientColor(new Color3f(turn ? c1 : c2));

					// Texturing
					TextureLoader loader = new TextureLoader(textures[turn ? 5 : 4], "LUMINANCE", new Container());
					Texture texture = loader.getTexture();
					texture.setBoundaryModeS(Texture.WRAP);
					texture.setBoundaryModeT(Texture.WRAP);
					texture.setBoundaryColor(new Color4f(0.0f, 0.0f, 0.0f, 0.0f));
					s.getAppearance().setTexture(texture);

					turn = !turn;
					this.setTitle("Tic Tac Toe     By: Jamie Langille    Turn: Player"
							+ (turn ? 1 : 2));
					checkWinner();
				}
			}
		}
	}

	/**
	 * Checks if someone has won the game. Players need to have 2 rows
	 * completed, in any dimension, to win.
	 */
	private void checkWinner() {

		// count how many wins each player has
		int p1 = 0, p2 = 0, n = 0;

		// for each piece...
		for (int x = 0; x < 3; x += 1) {
			for (int y = 0; y < 3; y += 1) {
				for (int z = 0; z < 3; z += 1) {

					// ignore empty spaces
					if (grid[x][y][z] == 0) {
						n++;
						continue;
					}

					// for each other piece...
					for (int x1 = 0; x1 < 3; x1 += 1) {
						for (int y1 = 0; y1 < 3; y1 += 1) {
							for (int z1 = 0; z1 < 3; z1 += 1) {

								/*
								 * ignore the same piece, or pieces that don't
								 * have anything <i>between</i> them.
								 */
								if (x == x1 && y == y1 && z == z1
										|| grid[x][y][z] != grid[x1][y1][z1]
												|| (x + x1) / 2.0 != (x + x1) / 2
												|| (y + y1) / 2.0 != (y + y1) / 2
												|| (z + z1) / 2.0 != (z + z1) / 2)
									continue;

								// if both points and the space b/w them are
								// equal,
								// that player is the winner! *Note: integer
								// division
								if (grid[x][y][z] == grid[(x + x1) / 2][(y + y1) / 2][(z + z1) / 2]) {
									if (grid[x][y][z] == 1)
										p1++;
									else
										p2++;
								}
							}
						}
					}
				}
			}
		}
		// 4 because this alg. finds double the number
		// of rows than expected (bi-directional)
		if (p1 == 4) {
			JOptionPane.showMessageDialog(this, "PLAYER 1 WINS!", "Winner", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		} else if (p2 == 4) {
			JOptionPane.showMessageDialog(this, "PLAYER 2 WINS!", "Winner", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		} else if (n == 27) {
			JOptionPane.showMessageDialog(this, "DRAW!", "DRAW!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public void mouseReleased(MouseEvent e) {
	}
}