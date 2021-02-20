## **Design of customer**
* Aggregate : customer
* Command : CustomerChargeCommand, CustomerCreateCommand, CustomerDepositCommand
* Events : CustomerChargedEvent, CustomerCreatedEvent, CustomerDepositedEvent
* query  : CustomerEntity, CustomerEntityRepository, CustomerProjector
* Adapter HTTP : CustomerController

## **Design of order**
* Aggregate : order
* Command : OrderCreateCommand, OrderFailCommand, OrderPayCommand, OrderFinishCommand
* Events : OrderCreatedEvent, OrderFailedEvent, OrderFinishedEvent, 
OrderPaidEvent, OrderPayFailedEvent
* query  : OrderEntity, OrderEntityRepository, OrderProjector
* Adapter HTTP : OrderController
* saga : OrderManagementSaga

## **Design of ticket**
* Aggregate : ticket
* Command : OrderTicketMoveCommand, OrderTicketPreserveCommand,
 OrderTicketUnlockCommand, TicketCreateCommand
* Events : OrderTicketMovedEvent, OrderTicketPreservedEvent, OrderTicketPreserveFailedEvent,
OrderTicketUnlockedEvent, TicketCreatedEvent
* query  : TicketEntity, TicketEntityRepository, TicketProjector
* Adapter HTTP : TicketController

## **Order saga workflow**
![ORDER SAGA WORKFLOW](./orderManagementSaga%20.svg)

## **Project Components**
* Aggregate
* Repository
* Projector
* Command Bus and Command Handler
* Event Bus and Event Handler and Event Store
* Saga pattern
* Query

## **Axon architecture**
![Axon architecture](./event%20sourcing%20and%20axon.svg)


## **Command workflow**
* Resource receive request and send to
CommandGateway
* CommandGateway execute Interceptors and
send to CommandBus
* CommandBus create UnitOfWork to associate
a transaction and handle command inside it
* CommandHandler use Repository to get a 
object aggregate
* CommandHandler execute apply method to
 trigger a event


## **Event workflow**
* CommandHandler execute apply method to
   trigger a event
* EventBus execute Interceptors on the event
* EventBus save the event to the eventStore
* EventBus call all event handlers registred on
the event
* Event handlers update aggregate , save vue model,
trigger another commands


## **Command vs Event**
* commands describe an intent to change the application's state. 
* command can only be processed once
* command can have return value
* command do not update data, it
do only some conditional check
and trigger the events to update data
* Events are objects that describe something 
that has occurred in the application. 
* event can be processed multiple times
* event has no return value
* event can be used to create aggregate
and saved into eventStore, it can be used
again to re-generate aggregate

## **Axon table explanation**
* association_value_entry : for saga
* domain_event_entry : for storing all the domain events
* saga_entry : for saga
* snapshot_event_entry : used for construction aggregate,
 fetch the value from this table, then fetch the events which
 is happening after snapshot from table domain_event_entry
 
 * tb_ticket, tb_order, tb_customer : table for query
 just for read
 
 
 ## **How to run project**
 * run the command **docker-compose up -d** at the root of project
 * build the project by maven and run 