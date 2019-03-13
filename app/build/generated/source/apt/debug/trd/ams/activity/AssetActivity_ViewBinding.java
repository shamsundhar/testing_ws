// Generated code from Butter Knife. Do not modify!
package trd.ams.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import trd.ams.R;

public class AssetActivity_ViewBinding implements Unbinder {
  private AssetActivity target;

  @UiThread
  public AssetActivity_ViewBinding(AssetActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AssetActivity_ViewBinding(AssetActivity target, View source) {
    this.target = target;

    target.txtLocationResult = Utils.findRequiredViewAsType(source, R.id.location_results, "field 'txtLocationResult'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AssetActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtLocationResult = null;
  }
}
