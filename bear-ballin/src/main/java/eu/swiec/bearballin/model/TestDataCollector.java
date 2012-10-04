package eu.swiec.bearballin.model;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import eu.swiec.bearballin.model.exceptions.EntityExistanceException;


public class TestDataCollector implements ITestData {

    final String processName;
    public List<TestDataEntity> testDataEntitiesList = new ArrayList<TestDataEntity>();

    public TestDataCollector(String processName) {
        this.processName = processName;
    }

    public class TestDataEntity {
        final public String stepId;
        final public String stepName;
        final public String dataName;
        final public Object data;
        final boolean isDefaultValue;

        public TestDataEntity(String stepId, String stepName, String dataName, Object data) {
            this(stepId, stepName, dataName, data, false);
        }

        public String getProcessName() {
            return processName;
        }

        /**
         * @param stepId
         * @param stepName
         * @param dataName
         * @param data
         * @param isDefault - is default means, it is constant (not taken from html)
         */
        public TestDataEntity(String stepId, String stepName, String dataName, Object data, boolean isDefault) {
            this.stepId = stepId;
            this.dataName = dataName;
            this.stepName = stepName;
            this.data = data;
            this.isDefaultValue = isDefault;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TestDataEntity) {
                TestDataEntity tdeObj = (TestDataEntity) obj;
                return (dataName == tdeObj.dataName);
            }
            return obj.toString().equals(dataName) || super.equals(obj);
        }

        @Override
        public String toString() {
            return dataName;
        }
    }

    public TestDataEntity getTestDataEntity(String dataName) {
        return getTestDataEntity(null, null, dataName);
    }

    public Object getTestEntityDataObject(String dataName) {
        return getTestDataEntity(null, null, dataName).data;
    }

    public TestDataEntity getTestDataEntity(String stepId, String dataName) {
        return getTestDataEntity(stepId, null, dataName);
    }

    public TestDataEntity getTestDataEntity(String stepId, String stepName, String dataName) {
        TestDataEntity searchedEntity = new TestDataEntity(stepId, stepName, dataName, new Object());
        if (testDataEntitiesList.contains(searchedEntity)) {
            return testDataEntitiesList.get(testDataEntitiesList.lastIndexOf(searchedEntity));
        } else
            return null;
    }

    public List<String> getStepsNames() {
        List<String> names = new ArrayList<String>();
        for (TestDataEntity tde : testDataEntitiesList) {
            names.add(tde.stepName);
        }
        return names;
    }

    public List<String> get(String attName) throws SecurityException, NoSuchFieldException {
        List<String> names = new ArrayList<String>();
        for (TestDataEntity tde : testDataEntitiesList) {
            names.add(tde.getClass().getField(attName).toString());
        }
        return names;
    }

    public List<String> getDataNames() {
        List<String> names = new ArrayList<String>();
        for (TestDataEntity tde : testDataEntitiesList) {
            names.add(tde.stepName);
        }
        return names;
    }

    public List<String> getStepsIds() {
        List<String> names = new ArrayList<String>();
        for (TestDataEntity tde : testDataEntitiesList) {
            names.add(tde.stepId);
        }
        return names;
    }

    public void collect(String stepId, String stepName, String dataName, Object data) {
        collect(stepId, stepName, dataName, data, null);
    }

    public void collect(String stepId, String stepName, String dataName, Object data, Format f) {
        if (f != null && data != null) {
            addValue(stepId, stepName, dataName, f.format(data), false);
        } else {
            addValue(stepId, stepName, dataName, data, false);
        }
    }

    public TestDataCollector addDefaultValue(String dataName, Object data) {
        return addValue("", "", dataName, data, true);
    }

    private TestDataCollector addValue(String stepId, String stepName, String dataName, Object data, boolean isDefaultValue) {
        TestDataEntity entity = new TestDataEntity(stepId, stepName, dataName, data);

        if (testDataEntitiesList.contains(entity)) {
            throw new EntityExistanceException("Test data entity already exists");
        } else {
            testDataEntitiesList.add(entity);
            return this;
        }
    }
}
