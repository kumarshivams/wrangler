# Data Prep (Enhanced)

[![cm-available](https://cdap-users.herokuapp.com/assets/cm-available.svg)](https://cdap.cask.co/)
[![cdap-transform](https://cdap-users.herokuapp.com/assets/cdap-transform.svg)](https://docs.cdap.io/cdap/current/en/transform/index.html)
[![Build Status](https://travis-ci.org/cdapio/hydrator-plugins.svg?branch=develop)](https://travis-ci.org/cdapio/hydrator-plugins)
[![Coverity Scan Build Status](https://scan.coverity.com/projects/11434/badge.svg)](https://scan.coverity.com/projects/hydrator-wrangler-transform)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.cdap.wrangler/wrangler-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.cdap.wrangler/wrangler-core)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Enhanced data transformation toolkit with new byte size and time duration parsing capabilities.

## New Features

### Byte Size and Time Duration Parsing
The Wrangler library now supports native parsing of byte size and time duration units:

#### Byte Size Units
- Supports: B, KB, MB, GB, TB (binary multiples, 1024-based)
- Example values: `10KB`, `1.5MB`, `3GB`
- Canonical unit: bytes (converted automatically)

#### Time Duration Units
- Supports: ns, μs, ms, s, m, h (nanoseconds to hours)
- Example values: `150ms`, `2.5s`, `1.5h`
- Canonical unit: nanoseconds (converted automatically)

### New Aggregate Directive
The `aggregate-stats` directive performs calculations using these units:

```plaintext
aggregate-stats <size_column> <time_column> <size_output_col> <time_output_col> [unit] [operation]
