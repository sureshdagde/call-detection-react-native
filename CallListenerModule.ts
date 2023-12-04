// CallListenerModule.ts
import {
  NativeEventEmitter,
  NativeModules,
  EmitterSubscription,
} from 'react-native';

const {CallListenerModule} = NativeModules;
const callEventsEmitter = new NativeEventEmitter(CallListenerModule);

export interface CallDetails {
  phoneNumber: string;
  callType: string;
  timestamp: number;
}

export class CallListener {
  private subscription: EmitterSubscription | null = null;

  public startListening(callback: (callDetails: CallDetails) => void): void {
    console.log(
        'Started startListening in modulw--------------------------------->',
      );
    this.subscription = callEventsEmitter.addListener(
      'LatestCallDetails',
      (callDetailsJson: string) => {
        try {
          const callDetails: CallDetails = JSON.parse(callDetailsJson);
          console.log('Received call details:', callDetails);
          callback(callDetails);
        } catch (error) {
          console.error('Error parsing call details JSON:', error);
        }
      },
    );
  }

  public stopListening(): void {
    console.log('Stopped calling --------------------------------->');

    if (this.subscription) {
      this.subscription.remove();
      this.subscription = null;
    }
  }
}

export default new CallListener();
