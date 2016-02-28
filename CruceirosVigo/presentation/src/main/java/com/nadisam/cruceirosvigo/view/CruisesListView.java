package com.nadisam.cruceirosvigo.view;

import com.nadisam.cruceirosvigo.model.CruiseModel;
import java.util.Collection;
import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link CruiseModel}.
 */
public interface CruisesListView extends LoadDataView {
  /**
   * Render a cruises list in the UI.
   *
   * @param cruiseModelCollection The collection of {@link CruiseModel} that will be shown.
   */
  void renderCruiseList(List<CruiseModel> cruiseModelCollection);
}
