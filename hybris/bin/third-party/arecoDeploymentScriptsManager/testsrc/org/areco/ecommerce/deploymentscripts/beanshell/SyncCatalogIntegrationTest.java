package org.areco.ecommerce.deploymentscripts.beanshell;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.ServicelayerTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.junit.Assert;
import org.apache.log4j.Logger;
import org.areco.ecommerce.deploymentscripts.core.AbstractWithConfigurationRestorationTest;
import org.areco.ecommerce.deploymentscripts.core.DeploymentScriptStarter;
import org.areco.ecommerce.deploymentscripts.core.ScriptExecutionResultDAO;
import org.areco.ecommerce.deploymentscripts.testhelper.DeploymentConfigurationSetter;
import org.areco.ecommerce.deploymentscripts.testhelper.DeploymentScriptResultAsserter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * It checks that the groovy scripts are working correctly.
 * The class can not synchronize with transaction, hence no transactions will be performed
 *
 * @author arobirosa
 */
@IntegrationTest
public class SyncCatalogIntegrationTest extends ServicelayerTest {
  private static final String RESOURCES_FOLDER = "/resources/test";
  private static final Logger LOG = Logger.getLogger(AbstractWithConfigurationRestorationTest.class);

  @Resource
  private FlexibleSearchService flexibleSearchService;
  @Resource
  private DeploymentConfigurationSetter deploymentConfigurationSetter;
  @Resource
  private DeploymentScriptResultAsserter deploymentScriptResultAsserter;
  @Resource
  private DeploymentScriptStarter deploymentScriptStarter;
  @Resource
  private ScriptExecutionResultDAO flexibleSearchScriptExecutionResultDao;

  @Before
  public void saveOldFolders() {
    if (LOG.isInfoEnabled()) {
      LOG.info("Saving current folders");
    }
    deploymentConfigurationSetter.saveCurrentFolders();
  }

  @After
  public void restoreOldFolders() {
    if (LOG.isInfoEnabled()) {
      LOG.info("Restoring old folders");
    }
    // We don't want to affect other tests
    this.deploymentConfigurationSetter.restoreOldFolders();
  }

  protected DeploymentConfigurationSetter getDeploymentConfigurationSetter() {
    return deploymentConfigurationSetter;
  }

  protected DeploymentScriptStarter getDeploymentScriptStarter() {
    return deploymentScriptStarter;
  }

  @Test
  public void testSyncCatalogOK() {
    this.getDeploymentConfigurationSetter().setTestFolders(RESOURCES_FOLDER, "synchronization_of_catalogs");
    this.getDeploymentConfigurationSetter().setEnvironment("DEV");
    final boolean wereThereErrors = this.getDeploymentScriptStarter().runAllPendingScripts();
    Assert.assertFalse("There were errors", wereThereErrors);
    getDeploymentScriptResultAsserter().assertResult("14112018_Ticket49",
      this.getFlexibleSearchScriptExecutionResultDao().getSuccessResult());
    MediaModel result = findMediaOnlineCatalog("ArecosSyncCataloguesImpexTest",
      "Online", "testMedia");
    Assert.assertNotNull(result);
  }

  protected DeploymentScriptResultAsserter getDeploymentScriptResultAsserter() {
    return deploymentScriptResultAsserter;
  }

  protected ScriptExecutionResultDAO getFlexibleSearchScriptExecutionResultDao() {
    return flexibleSearchScriptExecutionResultDao;
  }

  private MediaModel findMediaOnlineCatalog(final String catalogId, final String catalogVersionName, final String mediaCode) {
    ServicesUtil.validateParameterNotNull(catalogId, "catalog Id must not be null");
    ServicesUtil.validateParameterNotNull(catalogVersionName, "catalog Id must not be null");

    final StringBuilder sql = new StringBuilder(122);

    sql.append("SELECT {m.").append(MediaModel.PK);
    sql.append("} FROM { Media");
    sql.append(" as m ").append("JOIN ").append(CatalogVersionModel._TYPECODE);
    sql.append(" as cv ON {");
    sql.append("m.").append(MediaModel.CATALOGVERSION);
    sql.append("}={cv.").append(CatalogVersionModel.PK);
    sql.append("} AND {cv.").append(CatalogVersionModel.VERSION);
    sql.append("}=?");
    sql.append(CatalogVersionModel.VERSION);
    sql.append(" JOIN ").append(CatalogModel._TYPECODE).append(" as c ON ");
    sql.append("{cv.").append(CatalogVersionModel.CATALOG).append("}={c.").append(CatalogModel.PK).append("}");
    sql.append(" AND {c.").append(CatalogModel.ID).append("} = ?").append(CatalogModel.ID);
    sql.append(" } WHERE {m.").append(MediaModel.CODE);
    sql.append("} = ?").append(MediaModel.CODE);

    FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(sql);
    flexibleSearchQuery.addQueryParameter(CatalogModel.ID, catalogId);
    flexibleSearchQuery.addQueryParameter(CatalogVersionModel.VERSION, catalogVersionName);
    flexibleSearchQuery.addQueryParameter(MediaModel.CODE, mediaCode);
    return flexibleSearchService.searchUnique(flexibleSearchQuery);
  }
}
