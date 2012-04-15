package org.vaadin.risto.stepper.widgetset.client;

import org.vaadin.risto.stepper.widgetset.client.ui.VAbstractStepper;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.vaadin.terminal.gwt.client.communication.RpcProxy;
import com.vaadin.terminal.gwt.client.communication.StateChangeEvent;
import com.vaadin.terminal.gwt.client.ui.AbstractFieldConnector;

public abstract class AbstractStepperConnector<T, S> extends
        AbstractFieldConnector {

    private static final long serialVersionUID = 6952509590080940264L;

    @Override
    @SuppressWarnings("unchecked")
    public VAbstractStepper<T, S> getWidget() {
        return (VAbstractStepper<T, S>) super.getWidget();
    }

    @Override
    public AbstractStepperState getState() {
        return (AbstractStepperState) super.getState();
    }

    @Override
    protected void init() {
        super.init();

        final StepperRpc stepperRpcProxy = RpcProxy.create(StepperRpc.class,
                this);
        getWidget().addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                stepperRpcProxy.valueChange(event.getValue());
            }
        });
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {

        getWidget().setDisabled(!getState().isEnabled());
        getWidget().setReadonly(getState().isReadOnly());
        getWidget().setManualInputAllowed(getState().isManualInputAllowed());
        getWidget().setMouseWheelEnabled(getState().isMouseWheelEnabled());
        getWidget()
                .setInvalidValuesAllowed(getState().isInvalidValuesAllowed());

        getWidget().setMinValue(
                getWidget().parseStringValue(getState().getMinValue()));
        getWidget().setMaxValue(
                getWidget().parseStringValue(getState().getMaxValue()));

        getWidget().setValue(getState().getFieldValue());
        getWidget().setStepAmount(
                getWidget().parseStepAmount(getState().getStepAmount()));

        super.onStateChanged(stateChangeEvent);
    }
}
