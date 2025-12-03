#!/usr/bin/env python3
"""
Update README.md with Advent of Code progress from the leaderboard API.
Requires AOC_SESSION environment variable with your session cookie.
"""

import os
import re
import requests
from datetime import datetime

def get_aoc_stats(session_cookie, year=None):
    """Fetch stats from Advent of Code API."""
    # Auto-detect current year if not specified
    if year is None:
        now = datetime.now()
        # AoC runs in December, so use current year if in December, otherwise last year
        year = now.year if now.month == 12 else now.year - 1
    
    url = f"https://adventofcode.com/{year}"
    cookies = {"session": session_cookie}
    
    try:
        response = requests.get(url, cookies=cookies)
        response.raise_for_status()
        
        # Parse the HTML to extract stars
        html = response.text
        print(f"DEBUG: Fetched HTML length: {len(html)}")
        
        stars = {}
        
        # Find all day completions by checking the class on the <a> tag
        for day in range(1, 26):
            # Look for the <a> tag with calendar-dayN class
            day_pattern = rf'<a[^>]*class="[^"]*calendar-day{day}[^"]*"[^>]*>'
            matches = re.findall(day_pattern, html)
            
            if matches:
                link_tag = matches[0]
                # Check if it has calendar-verycomplete (2 stars) or calendar-complete (1 star)
                if 'calendar-verycomplete' in link_tag:
                    stars[day] = 2
                elif 'calendar-complete' in link_tag:
                    stars[day] = 1
                else:
                    stars[day] = 0
                    
                if stars[day] > 0:
                    print(f"DEBUG: Day {day} has {stars[day]} stars")
            else:
                stars[day] = 0
        
        print(f"DEBUG: Total stars found: {sum(stars.values())}")
        print(f"DEBUG: Stars dict: {stars}")
                
        return stars
    except Exception as e:
        print(f"Error fetching AoC stats: {e}")
        import traceback
        traceback.print_exc()
        return None

def update_readme(stars_dict, year):
    """Update README.md with the latest stats."""
    if not stars_dict:
        print("No stats to update")
        return
    
    readme_path = "README.md"
    
    with open(readme_path, 'r') as f:
        content = f.read()
    
    # Calculate totals
    total_stars = sum(stars_dict.values())
    days_completed = sum(1 for s in stars_dict.values() if s == 2)
    
    print(f"DEBUG: Updating README with {total_stars} stars, {days_completed} days completed")
    print(f"DEBUG: Stars breakdown: {stars_dict}")
    
    # Update badges
    content = re.sub(
        r'!\[Stars\]\(https://img\.shields\.io/badge/stars%20⭐-\d+-yellow\)',
        f'![Stars](https://img.shields.io/badge/stars%20⭐-{total_stars}-yellow)',
        content
    )
    
    content = re.sub(
        r'!\[Days Completed\]\(https://img\.shields\.io/badge/days%20completed-\d+-blue\)',
        f'![Days Completed](https://img.shields.io/badge/days%20completed-{days_completed}-blue)',
        content
    )
    
    # Update progress section
    content = re.sub(
        r'\*\*Total Stars:\*\* \d+/50',
        f'**Total Stars:** {total_stars}/50',
        content
    )
    
    content = re.sub(
        r'\*\*Days Completed:\*\* \d+/25',
        f'**Days Completed:** {days_completed}/25',
        content
    )
    
    # Find the table and ensure all released days are present
    # Days are released at midnight EST (10:30 AM IST)
    from datetime import datetime, timezone, timedelta
    
    # Get current day in EST timezone (UTC-5)
    est = timezone(timedelta(hours=-5))
    now_est = datetime.now(est)
    
    # Only in December
    if now_est.month == 12 and now_est.year == year:
        last_released_day = min(now_est.day, 25)  # AoC runs Dec 1-25
    else:
        last_released_day = 0
    
    print(f"DEBUG: Last released day: {last_released_day}")
    
    # Find the table section - look for year-specific table
    table_pattern = rf'(## 📅 {year}.*?\| Day \| Stars \| Solution \| Status \|\n\|-----|-------|----------|--------\|)\n(.*?)(\n---|\n##|\Z)'
    table_match = re.search(table_pattern, content, re.DOTALL)
    
    if table_match:
        table_header = table_match.group(1)
        existing_rows = table_match.group(2).strip()
        table_end = table_match.group(3)
        
        # Parse existing rows
        existing_days = set()
        row_pattern = r'\| \[Day (\d+)\]'
        for match in re.finditer(row_pattern, existing_rows):
            existing_days.add(int(match.group(1)))
        
        print(f"DEBUG: Existing days in table: {sorted(existing_days)}")
        
        # Build new table rows
        new_rows = []
        for day in range(1, last_released_day + 1):
            stars = stars_dict.get(day, 0)
            star_display = "⭐⭐" if stars == 2 else "⭐❓" if stars == 1 else "❓❓"
            status = "✅" if stars == 2 else "⏳" if stars == 1 else "❓"
            day_str = f"{day:02d}"
            
            row = f'| [Day {day}]({year}/day{day_str}) | {star_display} | [Java]({year}/day{day_str}/Solution.java) | {status} |'
            new_rows.append(row)
        
        # Replace the table
        new_table = table_header + '\n' + '\n'.join(new_rows) + table_end
        content = content[:table_match.start()] + new_table + content[table_match.end():]
    else:
        print("WARNING: Could not find table in README")
    
    with open(readme_path, 'w') as f:
        f.write(content)
    
    print(f"Updated README - Total Stars: {total_stars}, Days Completed: {days_completed}")

if __name__ == "__main__":
    session = os.environ.get("AOC_SESSION")
    
    if not session:
        print("AOC_SESSION environment variable not set")
        print("Get your session cookie from adventofcode.com and add it as a GitHub secret")
        exit(1)
    
    # Auto-detect year
    now = datetime.now()
    year = now.year if now.month == 12 else now.year - 1
    
    print(f"Fetching Advent of Code stats for {year}...")
    stats = get_aoc_stats(session, year)
    
    if stats:
        update_readme(stats, year)
        print("README updated successfully!")
    else:
        print("Failed to fetch stats")
        exit(1)
