package high.caliber.productions.demigod.utils;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

public class AnimationUtils {

	public static final String ANIMATIONS_HOME = "drawables/x32/animations/";

	Context context;

	public AnimationUtils(Context context) {
		this.context = context;
	}

	public class Fireball extends ImageView {

		Context context;

		AssetManager manager;
		Drawable fireball;
		AnimationDrawable anim = new AnimationDrawable();

		public Fireball(Context context) {
			super(context);
			this.context = context;

			manager = context.getAssets();

			try {
				fireball = Drawable.createFromStream(
						manager.open(ANIMATIONS_HOME + "fireball1.png"), null);
				anim.addFrame(fireball, 200);

				fireball = Drawable.createFromStream(
						manager.open(ANIMATIONS_HOME + "fireball2.png"), null);
				anim.addFrame(fireball, 200);

				anim.setOneShot(false);
			} catch (IOException e) {
				e.printStackTrace();
			}

			this.setImageDrawable(anim);

			return;
		}

		public void animateMovement(final int destX) {

			Handler handler = new Handler();
			handler.post(new Runnable() {

				@Override
				public void run() {

					for (int i = (int) getX(); i > destX; i--) {
						setX(i);
					}
					hit();
				}

			});

		}

		public void start() {
			anim.start();
		}

		private void hit() {

			AnimationDrawable anim = new AnimationDrawable();
			try {

				fireball = Drawable.createFromStream(
						manager.open(ANIMATIONS_HOME + "fireball3.png"), null);
				anim.addFrame(fireball, 200);

				fireball = Drawable.createFromStream(
						manager.open(ANIMATIONS_HOME + "fireball4.png"), null);
				anim.addFrame(fireball, 200);

				fireball = Drawable.createFromStream(
						manager.open(ANIMATIONS_HOME + "fireball5.png"), null);
				anim.addFrame(fireball, 200);

				anim.setOneShot(true);

				setImageDrawable(anim);
				anim.start();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}