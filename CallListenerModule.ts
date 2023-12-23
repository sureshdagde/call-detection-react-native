
import { NativeEventEmitter, NativeModules } from 'react-native';

interface CallStateModule extends NativeEventEmitter {
  emitCallStateUpdate: (state: string, number: string) => any;
}

const CallStateModule = NativeModules.CallStateModule as CallStateModule;

export default CallStateModule;
 