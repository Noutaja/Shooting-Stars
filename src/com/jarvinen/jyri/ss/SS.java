package com.jarvinen.jyri.ss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Main method of the game.
 */
public class SS extends Game {

	private static AssetManager AssetManager;
	GameController gc;

	static final String starFile = "sprites/star.png";
	static final String playerShipFile = "sprites/playerShip.png";
	static final String bulletDFile = "sprites/bulletD.png";
	static final String bulletUFile = "sprites/bulletU.png";
	static final String smallEnemyFile = "sprites/smallEnemyShip.png";
	static final String bigEnemyFile = "sprites/bigEnemyShip.png";
	static final String playerHitboxFile = "sprites/playerHitbox.png";
	static final String explosionFile = "sprites/explosion.png";
	static final String lifeFile = "sprites/life.png";
	static final String bombFile = "sprites/bomb.png";
	static final String fontFile = "MKOCR.ttf";
	static final String menuSelectedFile = "sprites/menuSelected.png";
	static final String mainLogoFile = "sprites/mainLogo.png";
	static final String screenFadeFile = "sprites/screenFade.png";
	static final String sbLogoFile = "sprites/sideBoardLogo.png";
	static final String sbBorderFile = "sprites/sideBoardBorder.png";
	static final String sbTextBgFile = "sprites/textBackground.png";
	static final String helpImageFile = "sprites/help.png";

	/**
	 * @param filename String pointing to the asset.
	 */
	static void LoadAsset(String filename){
		AssetManager.load(filename, Texture.class);
		AssetManager.finishLoading();
	}

	/**
	 * @param filename String pointing to the asset.
	 * @return
	 */
	static Texture GetAsset(String filename){
		return SS.AssetManager.get(filename, Texture.class);
	}

	/**
	 * Generates a font based on the font file and size.
	 * @param fontPath Name of the font.
	 * @param size Fontsize
	 * @return Returns the generated font.
	 */
	static BitmapFont GenerateFont(String fontPath, int size) {
		//copied from Aleksi Romppainen's Snek made during the course
		FileHandle fontFile = Gdx.files.internal(fontPath);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = size;

		BitmapFont generatedFont = generator.generateFont(parameter);
		return generatedFont;
	}

	/**
	 * Called at the start of the program.
	 */
	@Override
	public void create () {
		AssetManager = new AssetManager();
		gc = new GameController(this);
		//credit = new Credit(this);
	}

	/**
	 * Called every frame.
	 */
	@Override
	public void render () {
		gc.update(Gdx.graphics.getDeltaTime());
		screen.render(Gdx.graphics.getDeltaTime());
	}
}
