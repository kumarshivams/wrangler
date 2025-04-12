///*
// * Copyright © 2017-2019 Cask Data, Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License. You may obtain a copy of
// * the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations under
// * the License.
// */
//
//package io.cdap.wrangler.api.parser;
//
//public class TimeDuration extends Token {
//    private final long nanoseconds;
//
//    public TimeDuration(String value) {
//        super(TokenType.TIME_DURATION, value);
//        this.nanoseconds = parseDuration(value);
//    }
//
//    public long getNanoseconds() {
//        return nanoseconds;
//    }
//
//    public long getMilliseconds() {
//        return nanoseconds / 1_000_000;
//    }
//
//    public double getSeconds() {
//        return nanoseconds / 1_000_000_000.0;
//    }
//
//    private long parseDuration(String str) {
//        String numStr = str.replaceAll("[^0-9.]", "");
//        String unitStr = str.replaceAll("[^a-zA-Z]", "").toLowerCase();
//
//        double num = Double.parseDouble(numStr);
//
//        switch (unitStr) {
//            case "ns": return (long) num;
//            case "us": return (long) (num * 1_000);
//            case "ms": return (long) (num * 1_000_000);
//            case "s": return (long) (num * 1_000_000_000);
//            case "m": return (long) (num * 60 * 1_000_000_000L);
//            case "h": return (long) (num * 60 * 60 * 1_000_000_000L);
//            default: throw new IllegalArgumentException("Invalid time unit: " + unitStr);
//        }
//    }
//}

/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.wrangler.api.parser;

public class TimeDuration extends Token {
    private final long nanoseconds;

    public TimeDuration(String value) {
        super(TokenType.TIME_DURATION, value);
        this.nanoseconds = parseDuration(value);
    }

    public long getNanoseconds() {
        return nanoseconds;
    }

    public long getMilliseconds() {
        return nanoseconds / 1_000_000;
    }

    public double getSeconds() {
        return nanoseconds / 1_000_000_000.0;
    }

    private long parseDuration(String str) {
        String numStr = str.replaceAll("[^0-9.]", "");
        String unitStr = str.replaceAll("[^a-zA-Z]", "").toLowerCase();

        double num = Double.parseDouble(numStr);

        switch (unitStr) {
            case "ns":
                return (long) num;
            case "us":
                return (long) (num * 1_000);
            case "ms":
                return (long) (num * 1_000_000);
            case "s":
                return (long) (num * 1_000_000_000);
            case "m":
                return (long) (num * 60 * 1_000_000_000L);
            case "h":
                return (long) (num * 60 * 60 * 1_000_000_000L);
            default:
                throw new IllegalArgumentException("Invalid time unit: " + unitStr);
        }
    }
}