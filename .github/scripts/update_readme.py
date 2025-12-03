#!/usr/bin/env python3
"""
Update README.md with Advent of Code progress from the leaderboard API.
Requires AOC_SESSION environment variable with your session cookie.
"""

import os
import re
import requests
from datetime import datetime

def get_aoc_stats(session_cookie, year=2025):
    """Fetch stats from Advent of Code API."""
    url = f"https://adventofcode.com/{year}/leaderboard/self"
    cookies = {"session": session_cookie}
    
    try:
        response = requests.get(url, cookies=cookies)
        response.raise_for_status()
        
        # Parse the HTML to extract stars
        html = response.text
        stars = {}
        
        # Find all day completions (looking for star symbols)
        for day in range(1, 26):
            day_pattern = rf'<a.*?href="/{year}/day/{day}".*?>(.*?)</a>'
            matches = re.findall(day_pattern, html, re.DOTALL)
            
            if matches:
                content = matches[0]
                # Count stars (gold stars in the HTML)
                star_count = content.count('class="calendar-verycomplete"') * 2
                if star_count == 0:
                    star_count = content.count('class="calendar-complete"')
                stars[day] = star_count
            else:
                stars[day] = 0
                
        return stars
    except Exception as e:
        print(f"Error fetching AoC stats: {e}")
        return None

def update_readme(stars_dict):
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
    
    # Update table rows
    for day in range(1, 26):
        stars = stars_dict.get(day, 0)
        star_display = "⭐⭐" if stars == 2 else "⭐❓" if stars == 1 else "❓❓"
        status = "✅" if stars == 2 else "⏳" if stars == 1 else "❓"
        
        # Check if day directory exists
        day_str = f"{day:02d}"
        
        pattern = rf'\| \[Day {day}\]\(2025/day{day_str}\) \| [⭐❓]+ \| \[Java\]\(2025/day{day_str}/Solution\.java\) \| [✅⏳❓] \|'
        replacement = f'| [Day {day}](2025/day{day_str}) | {star_display} | [Java](2025/day{day_str}/Solution.java) | {status} |'
        
        if re.search(pattern, content):
            content = re.sub(pattern, replacement, content)
    
    with open(readme_path, 'w') as f:
        f.write(content)
    
    print(f"Updated README - Total Stars: {total_stars}, Days Completed: {days_completed}")

if __name__ == "__main__":
    session = os.environ.get("AOC_SESSION")
    
    if not session:
        print("AOC_SESSION environment variable not set")
        print("Get your session cookie from adventofcode.com and add it as a GitHub secret")
        exit(1)
    
    print("Fetching Advent of Code stats...")
    stats = get_aoc_stats(session)
    
    if stats:
        update_readme(stats)
        print("README updated successfully!")
    else:
        print("Failed to fetch stats")
        exit(1)
